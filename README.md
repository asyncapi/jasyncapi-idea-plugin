# AsyncAPI - IDEA plugin

> ⚠️ This plugin doesn't support AsyncAPI 1.x

Idea plugin for the [java-asyncapi](https://github.com/Pakisan/java-asyncapi) - Helps to edit and validate AsyncAPI schemas.

## Features
- Recognition of AsyncAPI specifications.
- AsyncAPI specification auto-completion.
- AsyncAPI specification example creation from `file` -> `new` -> `AsyncAPI specification`.
- AsyncAPI specification inspection and validation using AsyncAPI JSON Schema.
- Local references resolving with auto-completion in AsyncAPI schema.
- File references resolving with auto-completion in AsyncAPI schema.

## Usage
1. clone repository
```sh
git clone https://github.com/Pakisan/jasyncapi-idea-plugin.git
```
2. move to jasyncapi-idea-plugin
```sh
cd jasyncapi-idea-plugin
```
3. build plugin
```sh
./gradlew :buildPlugin
```
... should have no errors
4. verify plugin

NOTE: This will take a while the first time as it will have to download every single version of IntelliJ IDEA listed in the `runPluginVerifier` section of the `build.gradle.kts` file. 
```sh
./gradlew :runPluginVerifier
```
... should have no errors
5. install plugin from disk - [JetBrains Instruction](https://www.jetbrains.com/help/idea/managing-plugins.html#install_plugin_from_disk)