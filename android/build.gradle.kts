plugins {
    id("org.jetbrains.compose") version "1.1.1"
    id("com.android.application")
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.6.10"
}

group = "fr.dappli.photocloud"
version = "1.0"

repositories {
    jcenter()
}

android {
    compileSdkVersion(31)
    defaultConfig {
        applicationId = "fr.dappli.photocloud.android"
        minSdkVersion(24)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    namespace = "fr.dappli.photocloud.android"
}
kotlin {
    android()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":compose"))
                implementation(project(":sharedClient"))
                implementation("androidx.activity:activity-compose:1.4.0")
            }
        }
    }
}