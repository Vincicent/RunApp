plugins {
    alias(libs.plugins.runapp.android.library)
    alias(libs.plugins.runapp.android.room)
}

android {
    namespace = "com.vincicent.analytics.data"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.bundles.koin)


    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.analytics.domain)
}