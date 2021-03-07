plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
}

android {
  compileSdkVersion(BuildConfig.compileSdk)

  defaultConfig {
    applicationId = "org.mm2021uprising.idk"
    minSdkVersion(BuildConfig.minSdk)
    targetSdkVersion(BuildConfig.targetSdk)
    versionCode = BuildConfig.versionCode
    versionName = BuildConfig.versionName

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    buildFeatures {
      viewBinding = true
    }
  }
  signingConfigs {

    getByName("debug") {
      storeFile = File(rootDir, "/keystore/key.jks")
      storePassword = "uprising2021"
      keyAlias = "idk_debug"
      keyPassword = "uprising2021"
    }

    register("release") {
      storeFile = File(rootDir, "/keystore/key.jks")
      storePassword = "uprising2021"
      keyAlias = "idk"
      keyPassword = "uprising2021"
    }
  }

  buildTypes {

    getByName("debug") {
      isMinifyEnabled = false
      isDebuggable = true
      signingConfig = signingConfigs.getByName("debug")
    }

    getByName("release") {
      isMinifyEnabled = true
      isDebuggable = false
      signingConfig = signingConfigs.getByName("release")
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }

}

dependencies {

  implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
  coreLibraryDesugaring(CommonLibs.desugar_lib)

  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

  implementation(Kotlin.stdblib_jdk)
  implementation(KotlinCoroutine.android)

  //AndroidX
  implementation(AndroidXAppCompat.app_compat)
  implementation(AndroidXCore.core_ktx)
  implementation(AndroidXActivity.activity_ktx)
  implementation(AndroidXFragment.fragment_ktx)
  androidxFragment()
  androidXArch()
  implementation(AndroidXViewPager.view_pager_2)
  implementation(AndroidXViewPager.view_pager)
  implementation(AndroidXRecyclerView.recycler_view)
  implementation(AndroidXPreference.preference_ktx)

  implementation(Material.material)

  //Constraint Layout
  implementation(AndroidXConstraintLayout.constraint_layout)

  implementation(CommonLibs.timber)

  implementation(CommonLibs.dexter)
}