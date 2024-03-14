plugins {
    id("org.jetbrains.intellij") version "1.17.2"
    java
    kotlin("jvm") version "1.9.23"
}

group = "com.asyncapi.plugin.idea"
version = "2.5.0+jre17"

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
    version.set("2023.3.5")
    plugins.set(listOf("yaml"))
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    sinceBuild.set("223")
    untilBuild.set("233.*")
    changeNotes.set("""
        <h3>Changed</h3>
        <ul>
            <li>2.0.0 schema validation and completion:<ul>
                <li>Updated allowed extension name: <code>^x-[\w\d\-\_]+${'$'}</code> was changed to <code>^x-[\w\d\.\x2d_]+${'$'}</code></li>
                <li>OpenAPI schema v3</li>
                <li>Added <code>schemaFormat</code> to messages to recognize properly non AsyncAPI Schemas</li>
                <li>Was updated <code>JsonSchema</code> Draft 07</li>
            </ul>
            </li>
            <li>2.1.0 schema validation and completion:<ul>
                <li>OpenAPI schema v3</li>
                <li>Added <code>schemaFormat</code> to messages to recognize properly non AsyncAPI Schemas</li>
                <li>Was updated <code>JsonSchema</code> Draft 07</li>
            </ul>
            </li>
            <li>2.2.0 schema validation and completion:<ul>
                <li>OpenAPI schema v3</li>
                <li>Added <code>schemaFormat</code> to messages to recognize properly non AsyncAPI Schemas</li>
                <li>Was updated <code>JsonSchema</code> Draft 07</li>
            </ul>
            </li>
            <li>2.3.0 schema validation and completion:<ul>
                <li>OpenAPI schema v3</li>
                <li>Added <code>schemaFormat</code> to messages to recognize properly non AsyncAPI Schemas</li>
                <li>Was updated <code>JsonSchema</code> Draft 07</li>
            </ul>
            </li>
            <li>2.4.0 schema validation and completion:<ul>
                <li>OpenAPI schema v3</li>
                <li>Added <code>schemaFormat</code> to messages to recognize properly non AsyncAPI Schemas</li>
                <li>Was updated <code>JsonSchema</code> Draft 07</li>
            </ul>
            </li>
            <li>2.5.0 schema validation and completion:<ul>
                <li>OpenAPI schema v3</li>
                <li>Added <code>schemaFormat</code> to messages to recognize properly non AsyncAPI Schemas</li>
                <li>Was updated <code>JsonSchema</code> Draft 07</li>
            </ul>
            </li>
            <li>3.0.0 schema validation and completion:<ul>
                <li>Bindings:<ul>
                    <li>Solace:<ul>
                        <li>server binding 0.4.0</li>
                        <li>operation binding 0.4.0</li>
                    </ul>
                    </li>
                    <li>HTTP:<ul>
                        <li>message binding 0.3.0</li>
                        <li>operation binding 0.3.0</li>
                    </ul>
                    </li>
                </ul>
                </li>
            </ul>
            </li>
        </ul>
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
        "2023.3.2",
        "2023.3.3",
        "2023.3.4",
        "2023.3.5",
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
