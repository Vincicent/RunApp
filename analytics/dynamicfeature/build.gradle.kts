plugins {
    alias(libs.plugins.runapp.android.dynamic.feature)
}
android {
    namespace = "com.vincicent.analytics.dynamicfeature"
}

dependencies {
    implementation(project(":app"))

    api(projects.analytics.presentation)
    implementation(projects.analytics.domain)
    implementation(projects.analytics.data)
    implementation(projects.core.database)
}