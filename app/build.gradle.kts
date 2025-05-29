import java.util.Properties
import java.io.FileInputStream

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}
val apiBaseUrl = localProperties.getProperty("API_BASE_URL") ?: error("API_BASE_URL is not defined in local.properties")

 plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.momooczzi_fe"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.momooczzi_fe"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_BASE_URL", "\"$apiBaseUrl\"")

    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    implementation("com.tbuonomo:dotsindicator:4.3")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.android.gms:play-services-auth:20.6.0")
    implementation("com.kakao.sdk:v2-user:2.19.0")
    implementation("com.navercorp.nid:oauth:5.7.0") // 최신 버전은 공식 문서 참고
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("me.relex:circleindicator:2.1.6")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")

}

