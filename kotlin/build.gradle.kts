plugins {
  kotlin("jvm") version "2.1.0" // Use the latest Kotlin version
  application
}

group = "com.s13g.aoc"
version = "23"

application {
  mainClass.set("com.s13g.aoc.AdventOfCode")
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation("com.google.guava:guava:27.1-jre")
  testImplementation(kotlin("test"))
  testImplementation("org.junit.jupiter:junit-jupiter:5.10.0") // Latest JUnit version
}

tasks.test {
  useJUnitPlatform()
}