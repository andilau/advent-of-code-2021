import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "2.2.0"
}

application {
    mainClass.set("util.Runner")
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.reflections:reflections:0.10.2")
    implementation("org.slf4j:slf4j-nop:2.0.17")

    testApi("org.junit.jupiter:junit-jupiter-engine:5.13.1")
    testImplementation("org.assertj:assertj-core:3.27.3")
}

kotlin {
    jvmToolchain(11)
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}
