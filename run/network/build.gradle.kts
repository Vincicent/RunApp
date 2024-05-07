plugins {
    alias(libs.plugins.runapp.android.library)
}

android {
    namespace = "com.vincicent.run.network"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}