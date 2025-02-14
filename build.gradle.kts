plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.0"
    id("org.jetbrains.kotlin.jvm") version "1.9.23"
}

group = "com.gendtest.ai"
version = "1.0.1"

repositories {
    mavenCentral()
    maven { url = uri("https://www.jetbrains.com/intellij-repository/releases") }
}

intellij {
    version.set("2024.1.1")
    pluginName.set("GenDTest AI")
//    type.set("IU")
    plugins.set(listOf("java", "Kotlin"))
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.23")
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("231")
        untilBuild.set("") // No upper limit, works with future versions
    }
}
