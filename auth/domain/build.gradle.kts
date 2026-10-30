plugins {
    alias(libs.plugins.runapp.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
}