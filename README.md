# AsyncAPI - IDEA plugin

> ⚠️ This plugin doesn't support AsyncAPI 1.x

Idea plugin for the [java-asyncapi](https://github.com/asyncapi/jasyncapi) - Helps to edit and validate AsyncAPI specification files.

## Features
- Recognition of AsyncAPI specifications.
- AsyncAPI specification auto-completion.
- AsyncAPI specification example creation from `file` -> `new` -> `AsyncAPI specification`.
- AsyncAPI specification inspection and validation using [AsyncAPI JSON Schema](https://github.com/asyncapi/spec-json-schemas).
- Local references resolving with auto-completion in AsyncAPI specification.
- File references resolving with auto-completion in AsyncAPI specification.
- AsyncAPI specification preview as html in built-in/external browser.

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
## How to choose right version
If you want to build the plugin locally and/or install it from this repo - [check the compatibility table](./COMPATIBILITY.md)