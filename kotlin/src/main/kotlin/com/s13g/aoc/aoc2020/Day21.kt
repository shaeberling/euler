package com.s13g.aoc.aoc2020

import com.s13g.aoc.Result
import com.s13g.aoc.Solver

/**
 * --- Day 21: Allergen Assessment ---
 * https://adventofcode.com/2020/day/21
 */
class Day21 : Solver {
  override fun solve(lines: List<String>): Result {
    val foods = lines.map { parseLine(it) }

    // Allergen --> the ingredient they map to.
    val candidates = mutableMapOf<String, MutableSet<String>>()

    // At first, an allergens can be any kind of ingredient in their food.
    for (food in foods) {
      for (allergen in food.allergens) {
        if (allergen !in candidates) candidates[allergen] = food.ingredients.toMutableSet()
        // If the ingredient has candidates, intersect with new list to narrow down the options.
        else candidates[allergen] = candidates[allergen]!!.intersect(food.ingredients).toMutableSet()
      }
    }

    // We look at allergens that have only a single candidate, then set that and remove it from the other
    // candidate lists. We repeat the process until all allergens have been mapped to their incredient.
    val allergenIngredient = mutableMapOf<String, String>()
    while (candidates.isNotEmpty()) {
      for (can in candidates) {
        if (can.value.size == 1) {  // We found a match!
          val ingredient = can.value.first()
          allergenIngredient[can.key] = ingredient
          candidates.filter { ingredient in it.value }.forEach { it.value.remove(ingredient) }
        }
      }
      // Remove the found allergen from all candidate lists.
      allergenIngredient.forEach { candidates.remove(it.key) }
    }
    // Safe foods (non-allergens) are the ones that are not in the allergen list.
    val safeFoods = foods.flatMap { it.ingredients }.subtract(allergenIngredient.values)

    // Count how often safe foods are appearing in the ingredient lists.
    var resultA = 0
    for (food in foods) resultA += safeFoods.count { it in food.ingredients }

    // Create a sorted list of the allergen names, then use that order to produce a
    // comma-separated list of their matching ingredients.
    val allergensSorted = allergenIngredient.keys.toSortedSet()
    var resultB = allergensSorted.map { allergenIngredient[it] }.joinToString(",")
    return Result("$resultA", resultB)
  }

  private fun parseLine(line: String): Food {
    val split = line.split(" (contains ")
    return Food(split[0].split(' '), split[1].substring(0, split[1].lastIndex).split(',').map { it.trim() })
  }

  private data class Food(val ingredients: List<String>, val allergens: List<String>)
}