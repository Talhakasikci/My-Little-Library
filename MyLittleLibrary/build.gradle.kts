// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false

}
buildscript{
    repositories{
        google()
    }
    dependencies{
        val nav_version = "2.8.6"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")

    }
}