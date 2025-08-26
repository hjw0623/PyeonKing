plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.gradle)
}

android {
    namespace = "com.hjw0623.data"
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
    implementation(project(":core"))

    implementation(libs.androidx.core.ktx)

    //Coroutines
    implementation(libs.kotlinx.coroutines.android)

    //Networking
    implementation(libs.bundles.retrofit)
    ksp(libs.moshi.codegen)

    //Serialization
    implementation(libs.kotlinx.serialization.json)

    //Logging
    implementation(libs.timber)

    //DataStore
    implementation(libs.androidx.datastore.preferences)

    //Testing
    testImplementation(libs.bundles.testing)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.android.testing)

    //Debug
    debugImplementation(libs.bundles.debug.tooling)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}