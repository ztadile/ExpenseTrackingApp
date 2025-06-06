plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // ❌ Removed Compose plugin alias (not needed)
}

android {
    namespace = "com.example.expensetrackingapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.expensetrackingapp"
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

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
        compose = false  // ❗ Compose disabled
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // ✅ AppCompat + RecyclerView
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.recyclerview:recyclerview:1.4.0")

    // ✅ Gson for SharedPreferences storage
    implementation("com.google.code.gson:gson:2.10.1")

    // ✅ Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
