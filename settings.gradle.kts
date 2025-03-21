pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

// The following block registers dependencies to enable Kobweb snapshot support. It is safe to delete or comment out
// this block if you never plan to use them.
gradle.settingsEvaluated {
    fun RepositoryHandler.kobwebSnapshots() {
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") {
            content { includeGroupByRegex("com\\.varabyte\\.kobweb.*") }
            mavenContent { snapshotsOnly() }
        }
    }

    pluginManagement.repositories { kobwebSnapshots() }
    dependencyResolutionManagement.repositories { kobwebSnapshots() }
}

rootProject.name = "blogmultiplatform"

include(":site")
include(":worker")
