plugins {
    alias(libs.plugins.runapp.android.feature.ui)
}

android {
    namespace = "com.vincicent.auth.presentation"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
}