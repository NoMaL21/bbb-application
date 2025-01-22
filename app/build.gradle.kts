import java.util.Properties

val localProperties = Properties()
val localFile = rootProject.file("local.properties")
if (localFile.exists()) {
    localProperties.load(localFile.inputStream())
}
val serverIp = localProperties.getProperty("SERVER_IP") ?: "127.0.0.1"



plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.bbb_application"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.bbb_application"
        minSdk = 22
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "SERVER_IP", "\"$serverIp\"")
        }
        /*getByName("release") {
            buildConfigField("String", "SERVER_IP", "\"$serverIp\"")
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }*/
        release {
            buildConfigField("String", "SERVER_IP", "\"$serverIp\"")
            isMinifyEnabled = true
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
        compose = true
        buildConfig = true  // 추가된 부분

    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.transport.api)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}