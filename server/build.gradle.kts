plugins {
    kotlin("multiplatform")
    application //to run JVM part
    kotlin("plugin.serialization") version "1.6.10"
}

group = "fr.dappli.photocloud"
version = "1.0"

val ktorVersion = "1.6.7"
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
                implementation("io.ktor:ktor-serialization:$ktorVersion")
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-auth:$ktorVersion")
                implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
                implementation("ch.qos.logback:logback-classic:$logbackVersion")

                implementation(project(":commonAll"))
            }
        }
        val jvmTest by getting
    }
}

application {
    mainClass.set("ServerKt")
}