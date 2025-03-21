plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.1.0"
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.shoppingapp_user"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.shoppingapp_user"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    packaging{
        resources{
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/AL2.0"
            excludes += "META-INF/LGPL2.1"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //for Icons
    implementation("androidx.compose.material:material-icons-extended-android:1.7.6")

    // DI,Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    kapt("androidx.hilt:hilt-compiler:1.2.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.8.5")

    // Routes
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

    // Image
    implementation("io.coil-kt:coil-compose:2.7.0")
    // Image -/> AndroidView
    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")


    //custom bottom nev bar
    implementation ("com.canopas.compose-animated-navigationbar:bottombar:1.0.1")

    //loatty animation
    implementation("com.airbnb.android:lottie-compose:6.1.0")

//
//    implementation("androidx.compose.foundation:foundation:1.7.8") // Compose foundation elements ke liye
//    implementation("androidx.compose.ui:ui-graphics:1.7.8") // Brush aur Gradient ke liye
//    implementation("androidx.compose.ui:ui:1.7.8") // Compose UI core features ke liye
//// Material 3 for Jetpack Compose
//    implementation ("androidx.compose.material3:material3:1.3.1")  // Latest stable version


//    // fire base push notification
//    implementation("com.google.auth:google-auth-library-oauth2-http:1.30.1")
   implementation ("com.google.accompanist:accompanist-permissions:0.31.5-beta")

    // Supabase Storagee
    implementation("io.github.jan-tennert.supabase:storage-kt:3.1.2")
    implementation("io.ktor:ktor-client-cio:3.0.0")




}