plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.6.10"
    id("com.android.library")
    id("kotlin-parcelize")
}

group = "fr.dappli.photocloud"
version = "1.0"

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "sharedClient"
            export("com.arkivanov.essenty:lifecycle:0.2.2")
            export("com.arkivanov.decompose:decompose:0.5.2")
            export(project(":commonAll"))
        }
    }

    sourceSets {
        // common
        val commonMain by getting {
            dependencies {
                api("com.arkivanov.decompose:decompose:0.5.2")

                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

                api(libs.ktor.core)
                api(libs.ktor.auth)
                api(libs.ktor.client.negotiation)
                api(libs.ktor.serialization.kotlinx)

                api(project(":commonAll"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        // android
        val androidMain by getting {
            dependencies {
                api(libs.ktor.client.okhttp)
            }
        }
        val androidTest by getting

        // jvm
        val desktopMain by getting {
            dependencies {
                api(libs.ktor.client.okhttp)
                api(libs.ktor.serialization.jvm)
            }
        }

        // ios
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
    namespace = "fr.dappli.sharedclient"
}
