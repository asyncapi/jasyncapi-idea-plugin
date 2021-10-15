plugins {
    id("org.jetbrains.intellij") version "1.1.4"
    java
    kotlin("jvm") version "1.4.21"
}

group "com.asyncapi.plugin.idea"
version = "1.0.0+idea2021"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testCompile("junit", "junit", "4.12")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2021.2")
    plugins.set(listOf("yaml"))
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    sinceBuild.set("211.*")
    untilBuild.set("212.*")
    changeNotes.set("""
        <b>IDEA compatability:</b>
        <ul>
            <li>IDEA 2021.1</li>
            <li>IDEA 2021.2</li>
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
        
        <b>build: plugins</b>
        <ul>
            <li>org.jetbrains.intellij plugin was updated 0.6.5 -> 1.0</li>
        </ul>
        
        <b>fix: references indexing</b>
        <ul>
            <li>
                Was fixed insertion of references to schemas inside index.
                Before fix, I stored them under reference type. It was causing of wrong schema reference recognition with wrong file icon
            </li>
            <li>
                Was fixed recognition of referenced files inside of schema. Now referenced files gets asyncapi icon, but only
                after main schema indexing.
            </li>
            <li>
                Was fixed recognition of referenced files inside of schema. Now referenced files always gets asyncapi icon.
            </li>
        </ul>
    """.trimIndent())
}

tasks.getByName<org.jetbrains.intellij.tasks.RunPluginVerifierTask>("runPluginVerifier") {
    ideVersions.set(listOf("2021.1", "2021.1.1", "2021.1.2", "2021.2"))
    verifierVersion.set("1.266")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}