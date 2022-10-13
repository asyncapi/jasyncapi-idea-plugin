plugins {
    id("org.jetbrains.intellij") version "1.9.0"
    java
    kotlin("jvm") version "1.6.20"
}

group "com.asyncapi.plugin.idea"
version = "1.6.0+idea2021"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.0")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2022.1")
    plugins.set(listOf("yaml"))
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    sinceBuild.set("211")
    untilBuild.set("221.*")
    changeNotes.set("""
        <b>AsyncAPI Schema auto-completion</b>
        <b>New AsyncAPI version - 2.4.0</b>
        <b>IDEA: 2022.1.3, 2022.1.4</b>
    """.trimIndent())
}

tasks.getByName<org.jetbrains.intellij.tasks.RunPluginVerifierTask>("runPluginVerifier") {
    ideVersions.set(listOf(
        "2021.1",
        "2021.1.1",
        "2021.1.2",
        "2021.1.3",
        "2021.2",
        "2021.2.1",
        "2021.2.2",
        "2021.2.3",
        "2021.3",
        "2021.3.1",
        "2021.3.2",
        "2021.3.3",
        "2022.1",
        "2022.1.1",
        "2022.1.2",
        "2022.1.3",
        "2022.1.4",
    ))
    verifierVersion.set("1.284")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    test {
        useJUnitPlatform()
    }
}