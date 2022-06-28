import dependencies.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    signingConfigs {
        create("release") {}
    }
    compileSdk = 32

    defaultConfig {
        applicationId = "com.example.dictionary"
        minSdk = 23
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("Boolean", "isDebug", "false")
        }

        debug {
            buildConfigField("Boolean", "isDebug", "true")
        }
    }

    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(CORE_KTX_DEP)
    implementation(APP_COMPAT_DEP)
    implementation(MATERIAL_DEP)
    implementation(CONSTRAINT_LAYOUT_DEP)
    testImplementation(JUNIT_DEP)
    androidTestImplementation(ANDROID_JUNIT_DEP)
    androidTestImplementation(ESPRESSO_DEP)

    // ViewBindingPropertyDelegate
    implementation(VIEW_BINDING_DELEGATE_DEP)
    implementation(VIEW_BINDING_DELEGATE_DEP_NO_REF)

    // Room
    implementation(ROOM_DEP)
    kapt(KAPT_ROOM_DEP)

    // Gson
    implementation(GSON_DEP)

    // Koin
    implementation(KOIN_CORE)
    implementation(KOIN_ANDROID)

    // FragmentKTX
    implementation(FRAGMENT_KTX_DEP)

    // Retrofit
    implementation(RETROFIT_DEP)
    implementation(RETROFIT_CONVERTER_GSON_DEP)
    implementation(RETROFIT_COROUTINE_ADAPTER_DEP)

    // Logging Retrofit
    implementation(OKHTTP_LOGGING_DEP)

    // Coil
    implementation(COIL_DEP)

    // Coroutines
    implementation(COROUTINES_CORE_DEP)
    implementation(COROUTINES_ANDROID_DEP)
}