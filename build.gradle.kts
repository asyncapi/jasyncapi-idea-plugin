plugins {
    id("org.jetbrains.intellij") version "1.16.1"
    java
    kotlin("jvm") version "1.8.10"
}

group = "com.asyncapi.plugin.idea"
version = "2.4.0+jre17"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.15.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.2")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2023.3.1")
    plugins.set(listOf("yaml"))
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    sinceBuild.set("223")
    untilBuild.set("233.*")
    changeNotes.set("""
        <p>AsyncAPI 3.0.0</p>
        <p>IDEA 2023.3</p>
    """.trimIndent())
}

tasks.getByName<org.jetbrains.intellij.tasks.RunPluginVerifierTask>("runPluginVerifier") {
    ideVersions.set(listOf(
        "2022.3",
        "2022.3.1",
        "2022.3.2",
        "2022.3.3",
        "2023.1",
        "2023.1.1",
        "2023.1.2",
        "2023.1.3",
        "2023.1.4",
        "2023.1.5",
        "2023.2",
        "2023.2.1",
        "2023.2.2",
        "2023.2.3",
        "2023.2.4",
        "2023.2.5",
        "2023.3",
        "2023.3.1",
    ))
    verifierVersion.set("1.307")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "17"
    }
    test {
        useJUnitPlatform()
    }
}

kotlin {
    jvmToolchain {
        this.languageVersion.set(JavaLanguageVersion.of(17))
    }
}
