import java.text.SimpleDateFormat
import java.util.Date

buildscript {
    repositories {
        maven { url = uri("https://repo.spongepowered.org/repository/maven-public/") }
        mavenCentral()
    }
    dependencies {
        classpath("org.spongepowered:mixingradle:0.7-SNAPSHOT")
    }
}

plugins {
    eclipse
    idea
    id("net.minecraftforge.gradle") version "[6.0.16,6.2)"
    kotlin("jvm")
}

apply(plugin = "org.spongepowered.mixin")

val modId: String by project
val modGroupId: String by project
val modVersion: String by project
val modName: String by project
val modLicense: String by project
val modAuthors: String by project
val modDescription: String by project
val minecraftVersion: String by project
val minecraftVersionRange: String by project
val forgeVersion: String by project
val forgeVersionRange: String by project
val loaderVersionRange: String by project
val mappingChannel: String by project
val mappingVersion: String by project
val jeiVersion: String by project

group = modGroupId
version = modVersion

base {
    archivesName.set(modId)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

minecraft {
    mappings(project.property("mappingChannel") as String, project.property("mappingVersion") as String)

    accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))

    copyIdeResources.set(true)

    runs {
        configureEach {
            workingDirectory(project.file("run"))

            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")

            mods {
                create(modId) {
                    source(sourceSets.main.get())
                }
            }
        }

        create("client") {
            property("forge.enabledGameTestNamespaces", modId)
        }

        create("server") {
            property("forge.enabledGameTestNamespaces", modId)
            args("--nogui")
        }

        create("gameTestServer") {
            property("forge.enabledGameTestNamespaces", modId)
        }

        create("data") {
            workingDirectory(project.file("run-data"))
            args("--mod", modId, "--all", "--output", file("src/generated/resources/"), "--existing", file("src/main/resources/"))
        }
    }
}

configure<org.spongepowered.asm.gradle.plugins.MixinExtension> {
    add(sourceSets.main.get(), "${modId}.refmap.json")
    config("${modId}.main.mixins.json")
    config("${modId}.lib.mixins.json")
}

sourceSets.main.get().resources.srcDir("src/generated/resources")

repositories {
    maven {
        name = "Modmaven"
        url = uri("https://modmaven.dev/")
    }
    maven {
        name = "Kotlin for Forge"
        setUrl("https://thedarkcolour.github.io/KotlinForForge/")
    }
    mavenCentral()
}

dependencies {
    "minecraft"("net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}")
    implementation("thedarkcolour:kotlinforforge:4.11.0")

    compileOnly(fg.deobf("mezz.jei:jei-${minecraftVersion}-common-api:${jeiVersion}"))
    compileOnly(fg.deobf("mezz.jei:jei-${minecraftVersion}-forge-api:${jeiVersion}"))
    runtimeOnly(fg.deobf("mezz.jei:jei-${minecraftVersion}-forge:${jeiVersion}"))

    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")

}

tasks.named<ProcessResources>("processResources") {
    val replaceProperties = mapOf(
        "minecraft_version" to minecraftVersion,
        "minecraft_version_range" to minecraftVersionRange,
        "forge_version" to forgeVersion,
        "forge_version_range" to forgeVersionRange,
        "loader_version_range" to loaderVersionRange,
        "mod_id" to modId,
        "mod_name" to modName,
        "mod_license" to modLicense,
        "mod_version" to modVersion,
        "mod_authors" to modAuthors,
        "mod_description" to modDescription
    )

    inputs.properties(replaceProperties)

    filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta")) {
        expand(replaceProperties)
    }
}

jarJar.enable()

tasks.named<Jar>("jar") {
    manifest {
        attributes(mapOf(
            "Specification-Title" to modId,
            "Specification-Vendor" to modAuthors,
            "Specification-Version" to "1",
            "Implementation-Title" to project.name,
            "Implementation-Version" to archiveVersion.get(),
            "Implementation-Vendor" to modAuthors,
            "Implementation-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
        ))
    }

    finalizedBy("reobfJar")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}
