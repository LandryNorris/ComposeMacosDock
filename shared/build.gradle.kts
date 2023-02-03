plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("org.jetbrains.compose")
}

kotlin {
    macosArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material)
            }
        }
    }

    cocoapods {
        version = "1.0.0"
        homepage = "homepage"
        summary = "summary"
        license = "Apache 2.0"

        framework {

        }
    }
}
