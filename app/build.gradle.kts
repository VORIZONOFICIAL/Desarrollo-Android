plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.horza_one"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.horza_one"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // MPAndroidChart (ya lo tenías)
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // ==========================
    // DEP. DE TU CATÁLOGO (libs)
    // ==========================
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.legacy.support.v4)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // ====================================
    // RETROFIT + GSON + OKHTTP (AGREGADAS)
    // ====================================
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // ====================================
    // LIFECYCLE (Java)
    // ====================================
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime:2.7.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // ====================================
    // RECYCLERVIEW
    // ====================================
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // ====================================
    // CONCURRENT FUTURES (PARA EXECUTORS)
    // ====================================
    implementation("androidx.concurrent:concurrent-futures:1.1.0")

    // ====================================
    // TESTS (ya estaban)
    // ====================================
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
