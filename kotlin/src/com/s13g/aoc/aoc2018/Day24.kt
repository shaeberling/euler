/*
 * Copyright 2019 Sascha Haeberling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.s13g.aoc.aoc2018

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/** https://adventofcode.com/2018/day/24 */
class Day24 : Solver {
  override fun solve(lines: List<String>): Result {
    val env = parseGroups(lines)
    while (!env.isBattleOver()) fightStep(env)
    val solutionA = env.countUnitsRemaining().toString()
    // Find the lowest boost that makes the immune system win.
    for (boost in 1..100) {
      if (runBattle(env, boost)) break
    }
    val immuneUnitsLeft = env.immuneGroups.map { g -> g.numUnits }.sum()
    return Result(solutionA, immuneUnitsLeft.toString())
  }

  /** Returns whether the infection was killed. */
  private fun runBattle(env: Environment, boost: Int): Boolean {
    env.allGroupsSorted().forEach { g -> g.reset() }
    env.immuneGroups.forEach { g -> g.boost = boost }
    while (!env.isBattleOver()) {
      if (!fightStep(env)) break
    }
    return env.isInfectionDead()
  }

  /** Returns whether any units were lost. If false, battle is stuck. */
  private fun fightStep(env: Environment): Boolean {
    // Phase 1 - Target Selection (each group)
    val selectedTargets = HashMap<UnitGroup, UnitGroup>()
    selectedTargets.putAll(selectAllTargets(env.infectionGroups, env.immuneGroups))
    selectedTargets.putAll(selectAllTargets(env.immuneGroups, env.infectionGroups))

    // Attack!
    var unitsLost = 0
    for (attacker in env.allGroupsSorted()) {
      val defender = selectedTargets[attacker] ?: continue
      unitsLost += defender.attackedBy(attacker)
    }
    return unitsLost > 0
  }

  private fun selectAllTargets(attackers: List<UnitGroup>,
                               defenders: List<UnitGroup>): HashMap<UnitGroup, UnitGroup> {
    val selections = HashMap<UnitGroup, UnitGroup>()
    for (att in attackers.sortedWith(selectionComparator)) {
      if (att.numUnits == 0) continue
      var selectedDefender: UnitGroup? = null
      var selectedDamage = 0
      for (def in defenders) {
        if (def.numUnits == 0 || selections.values.contains(def)) continue
        val damage = calcDamage(att, def)
        if (damage < selectedDamage || damage == 0) continue
        if (damage > selectedDamage) {
          selectedDefender = def
          selectedDamage = damage
        } else if (damage == selectedDamage && selectedDefender != null) {
          if (def.effectivePower() > selectedDefender.effectivePower()) {
            selectedDefender = def
          } else if (def.effectivePower() == selectedDefender.effectivePower()) {
            if (def.initiative > selectedDefender.initiative) {
              selectedDefender = def
            } else {
              continue
            }
          }
        }
      }
      if (selectedDefender != null && selectedDamage > 0) {
        selections[att] = selectedDefender
      }
    }
    return selections
  }
}

private fun parseGroups(lines: List<String>): Environment {
  val immuneUnits = arrayListOf<UnitGroup>()
  val infectionUnits = arrayListOf<UnitGroup>()

  var infection = false
  for (line in lines) {
    if (line.startsWith("Infection")) {
      infection = true
      continue
    }
    if (line.isBlank() || line.startsWith("Immune")) continue
    addTo(if (infection) infectionUnits else immuneUnits, line, infection)
  }
  return Environment(
      immuneUnits.sortedByDescending { s -> s.effectivePower() },
      infectionUnits.sortedByDescending { s -> s.effectivePower() })
}

private fun addTo(list: ArrayList<UnitGroup>, line: String, infection: Boolean) {
  list.add(parseUnitGroup(line, list.size + 1, infection))
}

private fun parseUnitGroup(line: String, idx: Int, infection: Boolean): UnitGroup {
  val split = line.split(' ')
  val numUnits = split[0].toInt()
  val hitPoints = split[4].toInt()
  val initiative = split[split.size - 1].toInt()

  val tail = line.substringAfter("that does ").split(' ')
  val attackDamage = tail[0].toInt()
  val attackType = tail[1]

  val attributesStr = line.substringAfter('(', "").substringBefore(')')
  val attributesSplit = attributesStr.split("; ")
  val weaknesses = arrayListOf<String>()
  val immunities = arrayListOf<String>()
  for (a in attributesSplit) {
    if (a.startsWith("weak")) {
      weaknesses.addAll(a.substringAfter("weak to ").split(", "))
    } else {
      immunities.addAll(a.substringAfter("immune to ").split(", "))
    }
  }
  return UnitGroup(idx, numUnits, hitPoints, attackDamage, attackType, initiative,
      weaknesses, immunities, if (infection) "Infection" else "Immune System")
}

/** Used during selection stage. */
private val selectionComparator = Comparator<UnitGroup> { a, b ->
  if (b.effectivePower() == a.effectivePower()) {
    b.initiative - a.initiative
  } else {
    b.effectivePower() - a.effectivePower()
  }
}

/** Used during fight stage. */
private val initiativeComparator = Comparator<UnitGroup> { a, b ->
  // Descending order.
  b.initiative - a.initiative
}


private class Environment(val immuneGroups: List<UnitGroup>,
                          val infectionGroups: List<UnitGroup>) {
  fun allGroupsSorted(): List<UnitGroup> {
    val allGroups = arrayListOf<UnitGroup>()
    allGroups.addAll(immuneGroups)
    allGroups.addAll(infectionGroups)
    return allGroups.sortedWith(initiativeComparator)
  }

  fun isBattleOver(): Boolean {
    val numUnits1 = immuneGroups.map { g -> g.numUnits }.sum()
    val numUnits2 = infectionGroups.map { g -> g.numUnits }.sum()
    return numUnits1 == 0 || numUnits2 == 0
  }

  fun countUnitsRemaining(): Int {
    return allGroupsSorted().map { g -> g.numUnits }.sum()
  }

  fun isInfectionDead(): Boolean {
    return infectionGroups.map { g -> g.numUnits }.sum() == 0
  }
}


private fun calcDamage(attacker: UnitGroup, defender: UnitGroup): Int {
  if (attacker.numUnits == 0) return 0
  var damage =
      if (defender.immunities.contains(attacker.attackType)) 0
      else attacker.effectivePower()
  if (defender.weaknesses.contains(attacker.attackType)) damage *= 2
  return damage
}

private class UnitGroup(val idx: Int, var numUnits: Int, val hitPoints: Int,
                        val attackDamage: Int, val attackType: String,
                        val initiative: Int, val weaknesses: List<String>,
                        val immunities: List<String>, val group: String,
                        val origNumUnits: Int = numUnits, var boost: Int = 0) {
  fun effectivePower(): Int {
    return this.numUnits * (attackDamage + boost)
  }

  fun attackedBy(att: UnitGroup): Int {
    val damage = calcDamage(att, this)
    val unitsLost = minOf(damage / hitPoints, numUnits)
    numUnits -= unitsLost
    return unitsLost
  }

  fun reset() {
    numUnits = origNumUnits
  }

  fun id(): String {
    return "$group-$idx"
  }
}