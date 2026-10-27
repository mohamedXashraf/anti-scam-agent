plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "tech.mohamedashraf.data"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes.forEach {
        it.buildConfigField("String", "MUSE_API_KEY", project.properties["MUSE_API_KEY")
    }
    buildTypes.each {
        it.buildConfigField("Boolean", "IS_DEV", project.properties["dev"])
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    implementation(project(":domain"))

    implementation(libs.coroutines.core)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
}