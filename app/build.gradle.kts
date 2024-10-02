import java.io.ByteArrayOutputStream
import java.util.Properties
import java.io.FileInputStream
import java.io.FileOutputStream

plugins {
    autowire(libs.plugins.android.application)
    autowire(libs.plugins.kotlin.android)
    autowire(libs.plugins.kotlin.ksp)
}

fun getGitCommitHash(): String {
    val stdout = ByteArrayOutputStream()
    exec {
        commandLine = "git rev-parse --short HEAD".split(" ")
        standardOutput = stdout
    }
    return stdout.toString().trim()
}

fun getAndIncrementBuildNumber(): String {
    val propertiesFile = file("version.properties")
    val properties = Properties()

    // Load existing properties
    if (propertiesFile.exists()) {
        properties.load(FileInputStream(propertiesFile))
    } else {
        // If file doesn't exist, create a new one
        properties["BUILD_NUMBER"] = "1"
    }

    // Get the current build number
    val buildNumber = properties["BUILD_NUMBER"].toString().toInt()

    // Increment the build number
    properties["BUILD_NUMBER"] = (buildNumber + 1).toString()

    // Save the updated build number back to the properties file
    properties.store(FileOutputStream(propertiesFile), null)

    return buildNumber.toString()
}

android {
    namespace = property.project.app.packageName
    compileSdk = 34


    defaultConfig {
        applicationId = property.project.app.packageName
        minSdkVersion(rootProject.extra["defaultMinSdkVersion"] as Int)
        targetSdk = property.project.android.targetSdk
        versionName = property.project.app.versionName+".b"+getAndIncrementBuildNumber()+"."+getGitCommitHash()
        versionCode = property.project.app.versionCode
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf(
            "-Xno-param-assertions",
            "-Xno-call-assertions",
            "-Xno-receiver-assertions"
        )
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
        compose = true
    }
    lint { checkReleaseBuilds = false }
    ndkVersion = "27.0.11718014 rc1"
    buildToolsVersion = "34.0.0"
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // TODO Please visit https://highcapable.github.io/YukiHookAPI/en/api/special-features/host-inject
    // TODO 请参考 https://highcapable.github.io/YukiHookAPI/zh-cn/api/special-features/host-inject
    // androidResources.additionalParameters += listOf("--allow-reserved-package-id", "--package-id", "0x64")
}

dependencies {
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    // https://mvnrepository.com/artifact/dev.chrisbanes.haze/haze-jetpack-compose
    implementation("dev.chrisbanes.haze:haze:0.9.0-beta01")
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore.preferences.core.jvm)
    // https://mvnrepository.com/artifact/androidx.datastore/datastore-preferences
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("top.yukonga.miuix.kmp:miuix:0.2.4")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.valentinilk.shimmer:compose-shimmer:1.3.0")
    implementation("com.github.st235:expandablebottombar:1.5.3")
    implementation("me.nikhilchaudhari:composeNeumorphism:1.0.0-alpha02")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.7")
    implementation(libs.firebase.crashlytics.buildtools)
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    compileOnly(de.robv.android.xposed.api)
    implementation(com.highcapable.yukihookapi.api)
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    ksp(com.highcapable.yukihookapi.ksp.xposed)
    implementation(com.github.duanhong169.drawabletoolbox)
    implementation(androidx.core.core.ktx)
    implementation(androidx.appcompat.appcompat)
    implementation(com.google.android.material.material)
    implementation(androidx.constraintlayout.constraintlayout)
    testImplementation(junit.junit)
    androidTestImplementation(androidx.test.ext.junit)
    androidTestImplementation(androidx.test.espresso.espresso.core)
}
