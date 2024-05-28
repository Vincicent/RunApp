plugins {
    alias(libs.plugins.runapp.android.library)
}

android {
    namespace = "com.vincicent.analytics.data"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)


    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.analytics.domain)
}