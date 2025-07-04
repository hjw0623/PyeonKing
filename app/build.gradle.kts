plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.hjw0623.pyeonking"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hjw0623.pyeonking"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    /*kotlinOptions {
    jvmTarget = "17"
    }*/
    buildFeatures {
        compose = true
    }
}
kotlin {
    jvmToolchain(17)
}
dependencies {
    implementation(project(":core"))
    implementation(project(":presentation"))
    implementation(project(":data"))

    implementation(libs.androidx.core.ktx)

    //compose
    implementation(libs.bundles.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material.icons.extended)

    // Lifecycle & ViewModel
    implementation(libs.bundles.lifecycle)

    // Navigation
    implementation(libs.bundles.navigation)

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Networking (Retrofit & Coil)
    implementation(libs.bundles.retrofit)
    implementation(libs.coil.compose)

    // CameraX
    implementation(libs.bundles.camerax)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Logging
    implementation(libs.timber)

    // Testing
    testImplementation(libs.bundles.testing)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.android.testing)

    // Debug
    debugImplementation(libs.bundles.debug.tooling)
}