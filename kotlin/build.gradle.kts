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

tasks.register<Copy>("CopyAocInputs") {
  from("../data/aoc") // Adjust the path to your data folder
  into("${layout.projectDirectory}/src/main/resources/") // Destination inside the build folder
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

tasks.processResources {
  dependsOn("CopyAocInputs")
}