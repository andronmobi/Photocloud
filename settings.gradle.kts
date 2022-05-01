pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    
}
rootProject.name = "Photocloud"

include(":android")
include(":desktop")
include(":compose")
include(":commonAll")
include(":server")

include(":sharedClient")

enableFeaturePreview("VERSION_CATALOGS")
