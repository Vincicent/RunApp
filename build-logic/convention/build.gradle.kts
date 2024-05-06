import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    `kotlin-dsl`
}

group = "com.vincicent.runapp.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "vincicent.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}