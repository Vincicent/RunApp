plugins {
    alias(libs.plugins.runapp.android.dynamic.feature)
}
android {
    namespace = "com.vincicent.analytics.analytics_dynamic_feature"
}

dependencies {
    implementation(project(":app"))
    implementation(libs.androidx.navigation.compose)

    api(projects.analytics.presentation)
    implementation(projects.analytics.domain)
    implementation(projects.analytics.data)
    implementation(projects.core.database)
}