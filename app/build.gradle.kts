plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.androidx.room)
    id("kotlin-parcelize")
}

android {
    namespace = "com.soportereal.invefacon"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.soportereal.invefacon"
        minSdk = 25
        targetSdk = 35
        versionCode = 46
        versionName = "1.3.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    implementation(libs.material3)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    // Compose UI
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3.android)
    debugImplementation(libs.androidx.ui.tooling)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Material 3
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)

    // Accompanist (usa el alias corregido)
    implementation(libs.accompanist.systemuicontroller)

    // Navegación
    implementation(libs.androidx.navigation.compose)

    // Layouts
    implementation(libs.constraintlayout.compose)

    // Red y JSON
    implementation(libs.okhttp)
    implementation(libs.json)

    // Imágenes
    implementation(libs.coil.compose)
    implementation(libs.glide)
    ksp(libs.glide.compiler) // Cambiado de annotationProcessor a kapt

    // Impresión
    implementation(libs.escpos.thermalprinter.android.v330)

    // Test
    testImplementation(libs.junit)

    // Room para BD
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // GENERACION DE PDF
    implementation (libs.itext7.core) 
}
