plugins {
    alias(libs.plugins.runapp.android.library)
}

android {
    namespace = "com.vincicent.auth.data"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}