plugins {
    kotlin("multiplatform")
    application //to run JVM part
    kotlin("plugin.serialization") version "1.6.10"
}

group = "fr.dappli.photocloud"
version = "1.0"

val logbackVersion = "1.2.3"

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.auth)
                implementation(libs.ktor.serialization.kotlinx)
                implementation(libs.ktor.server.auth.jwt)
                implementation(libs.ktor.server.core)
                implementation(libs.ktor.server.netty)
                implementation(libs.ktor.server.negotiation)
                implementation(libs.ktor.server.cors)
                implementation(libs.ktor.server.compression)
                implementation(libs.ktor.server.status)

                implementation("ch.qos.logback:logback-classic:$logbackVersion")

                implementation("net.coobird:thumbnailator:0.4.17")

                implementation(project(":commonAll"))
            }
        }
        val jvmTest by getting
    }
}

application {
    mainClass.set("ServerKt")
}
