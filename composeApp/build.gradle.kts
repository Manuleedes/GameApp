import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }



        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = "ComposeApp"
                //isStatic = true
                linkerOpts("-lsqlite3")
            }
        }

        jvm()

        js {
            browser()
            binaries.executable()
        }

        @OptIn(ExperimentalWasmDsl::class)
        wasmJs {
            browser()
            binaries.executable()
        }

        sourceSets {
            androidMain.dependencies {
                implementation(compose.preview)
                implementation(libs.androidx.activity.compose)
            }
            commonMain.dependencies {
                implementation(projects.coreNetwork)
                implementation(projects.coreDatabase)

                implementation(projects.search.data)
                implementation(projects.search.domain)
                implementation(projects.search.ui)
                implementation(projects.game.data)
                implementation(projects.game.domain)
                implementation(projects.game.ui)

                implementation(projects.favorite.data)
                implementation(projects.favorite.domain)
                implementation(projects.favorite.ui)



                implementation(libs.navigation.compose)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.koin.core)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
            }
            commonTest.dependencies {
                implementation(libs.kotlin.test)
            }
            androidMain.dependencies {
                implementation(projects.coreDatabase)
                implementation(projects.search.ui)
                implementation(projects.game.ui)
                implementation(projects.favorite.ui)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)


            }

            iosMain.dependencies {
                implementation(projects.coreDatabase)
                implementation(projects.search.ui)
                implementation(projects.game.ui)
                implementation(projects.favorite.ui)
            }

            jvmMain.dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.kotlinx.coroutinesSwing)
                implementation(projects.coreDatabase)
                implementation(projects.search.ui)
                implementation(projects.game.ui)
                implementation(projects.favorite.ui)
            }
        }
    }

    android {
        namespace = "com.lidigu.gameapp"
        compileSdk = libs.versions.android.compileSdk.get().toInt()

        defaultConfig {
            applicationId = "com.lidigu.gameapp"
            minSdk = libs.versions.android.minSdk.get().toInt()
            targetSdk = libs.versions.android.targetSdk.get().toInt()
            versionCode = 1
            versionName = "1.0"
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    dependencies {
        debugImplementation(compose.uiTooling)
    }

    compose.desktop {
        application {
            mainClass = "com.lidigu.gameapp.MainKt"

            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageName = "com.lidigu.gameapp"
                packageVersion = "1.0.0"
            }
        }
    }

