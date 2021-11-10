plugins {
    id("org.jetbrains.intellij") version "1.2.1"
    java
    kotlin("jvm") version "1.4.21"
}

group "com.asyncapi.plugin.idea"
version = "1.2.0+idea2021"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("junit", "junit", "4.12")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2021.2")
    plugins.set(listOf("yaml"))
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    sinceBuild.set("211")
    untilBuild.set("212.*")
    changeNotes.set("""
        <b>Added</b>
        <ul>
          <li><code>2021.1</code> - <code>2021.3</code> Reload on change for AsyncAPI schema preview in built-in/external browser</li>
          <li><code>2021.2</code> - <code>2021.2.3</code> Reload on save for AsyncAPI schema preview in built-in/external browser</li>
        </ul>
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
        "2021.2.3"
    ))
    verifierVersion.set("1.268")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}