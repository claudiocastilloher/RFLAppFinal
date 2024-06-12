plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.refactoringlifeacademy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.refactoringlifeacademy"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //splash
    implementation ("androidx.core:core-splashscreen:1.0.1")
    //ViewModels//
    // Fragment
    implementation ("androidx.fragment:fragment-ktx:1.7.1")
    // Activity
    implementation ("androidx.activity:activity-ktx:1.9.0")
    // ViewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")
    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.1")
    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.7.2")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //picasso
    implementation ("com.squareup.picasso:picasso:2.71828")
    //navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")

    // corrutinas
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //Lifecycle components
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")


}
