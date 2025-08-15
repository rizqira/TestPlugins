import com.android.build.gradle.BaseExtension
import com.lagradost.cloudstream3.gradle.CloudstreamExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.7.3") // jangan diganti
        classpath("com.github.recloudstream:gradle:-SNAPSHOT")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.22")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

fun Project.cloudstream(configuration: CloudstreamExtension.() -> Unit) =
    extensions.getByName<CloudstreamExtension>("cloudstream").configuration()

fun Project.android(configuration: BaseExtension.() -> Unit) =
    extensions.getByName<BaseExtension>("android").configuration()

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "com.lagradost.cloudstream3.gradle")

    cloudstream {
        setRepo(System.getenv("GITHUB_REPOSITORY") ?: "https://github.com/tekuma25/Indostream")
        authors = listOf("TeKuma25")
    }

    android {
        namespace = "com.tekuma25"

        defaultConfig {
            minSdk = 26
            compileSdkVersion(35)
            targetSdk = 35
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        tasks.withType<KotlinJvmCompile>().configureEach {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs += listOf(
                    "-Xno-call-assertions",
                    "-Xno-param-assertions",
                    "-Xno-receiver-assertions"
                )
            }
        }
    }

    dependencies {
        val cloudstream by configurations
        val implementation by configurations

        // Cloudstream dependencies
        cloudstream("com.lagradost:cloudstream3:pre-release")

        // Kotlin Standard Library
        implementation(kotlin("stdlib"))

        // HTTP requests
        implementation("com.github.Blatzar:NiceHttp:0.4.11")
        implementation("com.squareup.okhttp3:okhttp:4.12.0")

        // Parsing HTML
        implementation("org.jsoup:jsoup:1.18.3")

        // JSON
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
        implementation("com.google.code.gson:gson:2.11.0")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

        // Coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

        // JavaScript engine
        implementation("com.faendir.rhino:rhino-android:1.6.0")
        implementation("app.cash.quickjs:quickjs-android:0.9.2")

        // Fuzzy matching
        implementation("me.xdrop:fuzzywuzzy:1.4.0")

        // Android util
        implementation("androidx.core:core-ktx:1.16.0")
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
