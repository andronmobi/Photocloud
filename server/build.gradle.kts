plugins {
    kotlin("multiplatform")
    application //to run JVM part
    kotlin("plugin.serialization") version "1.5.31"
}

group = "fr.dappli"
version = "1.0"

val kotlinVersion = "1.5.31"
val serializationVersion = "1.3.0"
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

                // TODO move to model and change to api
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
            }
        }
        val jvmTest by getting
    }
}

application {
    mainClass.set("ServerKt")
}