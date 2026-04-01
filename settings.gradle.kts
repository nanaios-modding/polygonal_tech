pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            name = "MinecraftForge"
            url = uri("https://maven.minecraftforge.net/")
        }
    }
    plugins {
        kotlin("jvm") version "1.9.24"
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "polygonal_tech"
