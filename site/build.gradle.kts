import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.serialization.plugin)
    // alias(libs.plugins.kobwebx.markdown)
}

group = "org.example.blogmultiplatform"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
        }
    }
}

kotlin {
    configAsKobwebApplication("blogmultiplatform", includeServer = true)

    sourceSets {
        commonMain.dependencies {
          // Add shared dependencies between JS and JVM here
            implementation(libs.compose.runtime)
            implementation(libs.kotlinx.serialization)
        }
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)
            implementation(libs.kotlinx.serialization)
            implementation("io.ktor:ktor-client-core:2.3.6")
            //implementation("io.ktor:ktor-client-cio:2.3.6")
            implementation("io.ktor:ktor-client-js:2.3.6")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.6")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.6")
            //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

            // implementation(libs.kobwebx.markdown)
            implementation(project(":worker"))
        }
        jvmMain.dependencies {
            compileOnly(libs.kobweb.api) // Provided by Kobweb backend at runtime
            //implementation(libs.mongodb.kotlin.driver)
            implementation(libs.kotlinx.serialization)
            implementation(libs.mongodb.driver.core)
            implementation(libs.mongodb.driver.sync)
        }
    }
}
dependencies {
}
