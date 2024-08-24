plugins {
    id("org.jetbrains.intellij.platform") version "2.0.1"
    id("org.jetbrains.intellij.platform.migration") version "2.0.1"
    java
    kotlin("jvm") version "2.0.20"
}

group = "com.asyncapi.plugin.idea"
version = "2.6.0+jre21"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-core:2.17.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.11.0")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2024.2")
    plugins.set(listOf("yaml"))
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    sinceBuild.set("223")
    untilBuild.set("242.*")
    changeNotes.set("""
        <h3>Added</h3>
        <ul>
            <li>IDEA 2024.2</li>
        </ul>
        <h3>Fixed</h3>
        <ul>
            <li>No fixes this release version</li>
        </ul>
        <h3>Changed</h3>
        <ul>
            <li>Gradle now 8.10</li>
            <li>Plugin verifier now 1.373</li>
            <li>Now using Github Dependabot</li>
            <li>bump actions/checkout from 3 to 4</li>
            <li>bump actions/setup-node from 3 to 4</li>
            <li>bump actions/stale from 5.2.0 to 9.0.0</li>
            <li>bump amannn/action-semantic-pull-request from 5.2.0 to 5.5.3</li>
            <li>bump com.fasterxml.jackson.core:jackson-core from 2.15.0 to 2.17.2</li>
            <li>bump com.fasterxml.jackson.dataformat:jackson-dataformat-yaml from 2.15.0 to 2.17.2</li>
            <li>bump com.fasterxml.jackson.module:jackson-module-kotlin from 2.15.0 to 2.17.2</li>
            <li>bump hmarr/auto-approve-action from 3.2.1 to 4.0.0</li>
            <li>bump jvm from 1.9.23 to 2.0.20</li>
            <li>bump marocchino/sticky-pull-request-comment from 2.5.0 to 2.9.0</li>
            <li>bump org.jetbrains.intellij from 1.17.3 to 1.17.4</li>
            <li>bump org.junit.jupiter:junit-jupiter-api from 5.9.2 to 5.11.0</li>
            <li>bump org.junit.jupiter:junit-jupiter-engine from 5.9.2 to 5.11.0</li>
            <li>bump org.junit.vintage:junit-vintage-engine from 5.9.2 to 5.11.0</li>
            <li>bump pascalgn/automerge-action from 0.15.6 to 0.16.3</li>
        </ul>
    """.trimIndent()
    )
}

tasks.getByName<org.jetbrains.intellij.tasks.RunPluginVerifierTask>("runPluginVerifier") {
    ideVersions.set(
        listOf(
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
            "2023.3.2",
            "2023.3.3",
            "2023.3.4",
            "2023.3.5",
            "2023.3.6",
            "2024.1",
            "2024.2"
        )
    )
    verifierVersion.set("1.373")
}

tasks {
    compileKotlin {
//        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
    }
    compileTestKotlin {
//        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
    }
    test {
        useJUnitPlatform()
    }
}

kotlin {
    jvmToolchain {
        this.languageVersion.set(JavaLanguageVersion.of(21))
    }
}
