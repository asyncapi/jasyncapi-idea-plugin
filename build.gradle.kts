import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("org.jetbrains.intellij.platform") version "2.0.1"
    id("org.jetbrains.intellij.platform.migration") version "2.0.1"
    java
    kotlin("jvm") version "2.0.20"
}

group = "com.asyncapi.plugin.idea"
version = "2.6.0+jre17"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.2.0.2", useInstaller = false)

        // Plugin Dependencies. Uses `platformBundledPlugins` property from the gradle.properties file for bundled IntelliJ Platform plugins.
        bundledPlugins(listOf(
            "org.jetbrains.plugins.yaml"
        ))

        instrumentationTools()
        pluginVerifier()
        testFramework(TestFrameworkType.Platform)
    }

    implementation("com.fasterxml.jackson.core:jackson-core:2.17.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.17.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.11.0")
}

// See https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html
intellijPlatform {
    instrumentCode = true

    pluginConfiguration {
        description = providers.fileContents(
            layout.projectDirectory.file("src/main/resources/META-INF/description.html")
        ).asText

        changeNotes = """
            <h3>Added</h3>
            <ul>
                <li>IDEA 2024.2</li>
            </ul>
        """.trimIndent()
    }
}

tasks {
    patchPluginXml {
        sinceBuild = "223"
        untilBuild = "242.*"
    }
}

//tasks.getByName<org.jetbrains.intellij.tasks.RunPluginVerifierTask>("runPluginVerifier") {
//    ideVersions.set(
//        listOf(
//            "2022.3",
//            "2022.3.1",
//            "2022.3.2",
//            "2022.3.3",
//            "2023.1",
//            "2023.1.1",
//            "2023.1.2",
//            "2023.1.3",
//            "2023.1.4",
//            "2023.1.5",
//            "2023.2",
//            "2023.2.1",
//            "2023.2.2",
//            "2023.2.3",
//            "2023.2.4",
//            "2023.2.5",
//            "2023.3",
//            "2023.3.1",
//            "2023.3.2",
//            "2023.3.3",
//            "2023.3.4",
//            "2023.3.5",
//            "2023.3.6",
//            "2024.1",
//            "2024.2"
//        )
//    )
//    verifierVersion.set("1.373")
//}

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
