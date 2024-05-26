plugins {
    alias(libs.plugins.runapp.android.library)
    alias(libs.plugins.runapp.android.room)
}

android {
    namespace = "com.vincicent.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
}