plugins {
    alias(libs.plugins.runapp.android.library)
    alias(libs.plugins.runapp.jvm.ktor)
}

android {
    namespace = "com.vincicent.run.network"
}

dependencies {
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(projects.core.data)
}