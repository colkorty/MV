plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.colkorty.mv"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.colkorty.mv"
        minSdk = 36
        targetSdk = 36
        versionCode = 4
        versionName = "4: cute notifications"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
}

dependencies {
    implementation("androidx.work:work-runtime-ktx:2.7.1")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.github.exjunk:ThanosEffect:1.0.2")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.splashscreen)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}