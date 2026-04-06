plugins {
    alias(libs.plugins.android.application)

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    jacoco
}

android {
    namespace = "com.vladan.pricetracker"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.vladan.pricetracker"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            enableUnitTestCoverage = true
        }

        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    sourceDirectories.setFrom(files("src/main/java"))
    classDirectories.setFrom(
        fileTree("${layout.buildDirectory.get().asFile}/intermediates/classes/debug/transformDebugClassesWithAsm/dirs") {
            exclude(
                "**/R.class",
                "**/R\$*.class",
                "**/BuildConfig.*",
                "**/Manifest*.*",
                // Hilt / Dagger generated
                "**/Hilt_*.*",
                "**/Dagger*.*",
                "**/*_Factory*.*",
                "**/*_HiltModules*.*",
                "**/*_HiltComponents*.*",
                "**/*_MembersInjector*.*",
                "**/*_ComponentTreeDeps*.*",
                "**/*_GeneratedInjector*.*",
                "**/hilt_aggregated_deps/**",
                // Compose generated
                "**/*ComposableSingletons*.*",
                // App & Activity classes
                "**/PriceTrackerApp*.*",
                "**/MainActivity*.*",
                // Core package
                "**/core/**",
                // DI modules
                "**/di/**",
                // Presentation layer (screens, components, UI state) - keep ViewModels
                "**/presentation/components/**",
                "**/presentation/*Screen*.*",
                "**/presentation/*UiState*.*"
            )
        }
    )
    executionData.setFrom(
        files("${layout.buildDirectory.get().asFile}/outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec")
    )
}
