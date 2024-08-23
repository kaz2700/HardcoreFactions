import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.diffplug.spotless") version "6.25.0"
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    implementation("org.postgresql:postgresql:42.3.1")
    implementation("com.google.code.gson:gson:2.11.0")
}


tasks.withType<ShadowJar> {

    project.configurations.implementation { isCanBeResolved = true }
    configurations = listOf(project.configurations["implementation"])
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

spotless {
    java {
        eclipse()

        licenseHeader("/* (Copyright) 2024 github.com/kaz2700 */")
    }
}

tasks.assemble {
    dependsOn(tasks.reobfJar)
}