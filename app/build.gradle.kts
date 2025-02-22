plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.practice"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.practice"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        debug {
            isMinifyEnabled =false

        }
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    //coroutines
    implementation(libs.kotlinx.coroutines.android)

    //viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //for by viewModels() in activity
    implementation ("androidx.activity:activity-ktx:1.8.2")
    // for by viewModels() in fragment
    implementation ("androidx.fragment:fragment-ktx:1.6.2")
    // for viewModelProviders.of(context).get(viewModel)
    implementation ("androidx.lifecycle:lifecycle-extensions:2.1.0")
    //livedata
    implementation(libs.androidx.lifecycle.livedata.ktx)
    //glide
    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //paging 3
    val paging_version = "3.2.1"
    implementation("androidx.paging:paging-runtime:$paging_version")

    implementation(libs.hilt.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}