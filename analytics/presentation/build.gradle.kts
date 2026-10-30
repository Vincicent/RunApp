plugins {
    alias(libs.plugins.runapp.android.feature.ui)
}

android {
    namespace = "com.vincicent.analytics.presentation"
}

dependencies {

    implementation(projects.analytics.domain)
}