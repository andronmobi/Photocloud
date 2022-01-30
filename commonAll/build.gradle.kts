plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.6.10"
}

group = "fr.dappli.photocloud"
version = "1.0"

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
            }
        }
    }
}
