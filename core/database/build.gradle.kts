plugins {
    alias(libs.plugins.runapp.android.library)
    alias(libs.plugins.runapp.android.room)
}

android {
    namespace = "com.vincicent.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)

    implementation(projects.core.domain)
}