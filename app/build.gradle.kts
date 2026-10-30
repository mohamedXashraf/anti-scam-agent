plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics.gradle)
}

android {
    namespace = "tech.mohamedashraf.antiscamagent"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "tech.mohamedashraf.antiscamagent"
        minSdk = 24
        targetSdk = 37
        versionCode = 100
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":presentation"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.android.compose)
    implementation(libs.koin.android.coroutines)

    implementation(libs.retrofit)
}