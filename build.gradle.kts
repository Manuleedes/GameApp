plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.kotlinSerialization) apply false
}

allprojects {
    configurations.all {
        resolutionStrategy {
            force("org.jetbrains.compose.runtime:runtime:1.7.3")
            force("org.jetbrains.compose.runtime:runtime-saveable:1.7.3")
            force("org.jetbrains.compose.foundation:foundation:1.7.3")
            force("org.jetbrains.compose.foundation:foundation-layout:1.7.3")
            force("org.jetbrains.compose.ui:ui:1.7.3")
            force("org.jetbrains.compose.ui:ui-geometry:1.7.3")
            force("org.jetbrains.compose.ui:ui-graphics:1.7.3")
            force("org.jetbrains.compose.ui:ui-text:1.7.3")
            force("org.jetbrains.compose.ui:ui-unit:1.7.3")
            force("org.jetbrains.compose.ui:ui-util:1.7.3")
            force("org.jetbrains.compose.material3:material3:1.7.3")
            force("org.jetbrains.compose.animation:animation:1.7.3")
            force("org.jetbrains.compose.animation:animation-core:1.7.3")
        }
    }
}