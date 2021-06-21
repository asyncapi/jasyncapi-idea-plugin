plugins {
    id("org.jetbrains.intellij") version "0.6.5"
    java
    kotlin("jvm") version "1.4.21"
}

group "com.asyncapi.plugin.idea"
version = "1.0.0-EAP-1+idea2021-snapshot"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testCompile("junit", "junit", "4.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "2021.1.2"
    setPlugins("yaml")
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    sinceBuild("211")
    untilBuild("212.*")
    changeNotes("""
        <b>IDEA compatability:</b>
        <ul>
            <li>IDEA 2021.1.*</li>
        </ul>
        
        <b>refactor: update the way to get the schema</b>
        <ul>
            <li>AsyncAPIJsonSchemaInspection uses ResourceUtil.getResource(ClassLoader, String, String) instead of deprecated ResourceUtil.getResource(Class, String, String)</li>
            <li>AsyncAPIYamlSchemaInspection uses ResourceUtil.getResource(ClassLoader, String, String) instead of deprecated ResourceUtil.getResource(Class, String, String)</li>
        </ul>
        
        <b>feature: svg icon</b>
        <ul>
            <li>asyncapi.png was replaced with asyncapi.svg</li>
            <li>deprecated method IconLoader.getIcon(String) was replaced with IconLoader.getIcon(String, Class)</li>
        </ul>
    """.trimIndent())
}

tasks.getByName<org.jetbrains.intellij.tasks.RunPluginVerifierTask>("runPluginVerifier") {
    setIdeVersions(listOf("2021.1", "2021.1.1", "2021.1.2"))
    verifierVersion("1.256")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}