plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    kotlin("jvm") version "1.9.24"
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(8)
}