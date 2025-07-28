plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.hjw0623.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
kotlin {
    jvmToolchain(21)
}
dependencies {
    implementation(libs.androidx.core.ktx)

    //compose
    implementation(libs.bundles.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material.icons.extended)

    // CameraX
    implementation(libs.bundles.camerax)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Logging
    implementation(libs.timber)

    //GoogleMap
    implementation(libs.bundles.googlemap)

    //Coil
    implementation(libs.bundles.coil)

    //DataStore
    implementation(libs.androidx.datastore.preferences)

    // Testing
    testImplementation(libs.bundles.testing)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.android.testing)

    // Debug
    debugImplementation(libs.bundles.debug.tooling)
}