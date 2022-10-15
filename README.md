# AsyncAPI - IDEA plugin

> ⚠️ This plugin doesn't support AsyncAPI 1.x

Idea plugin for the [java-asyncapi](https://github.com/Pakisan/java-asyncapi) - Helps to edit and validate AsyncAPI schemas.

## Features
- Recognition of AsyncAPI schemas.
- AsyncAPI schema example creation from `file` -> `new` -> `AsyncAPI schema`.
- AsyncAPI schema inspection and validation using AsyncAPI JSON Schema.
- Local references resolving with auto-completion in AsyncAPI schema (json).
- File references resolving with auto-completion in AsyncAPI schema (json).

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
➜  jasyncapi-idea-plugin git:(feature/idea-plugin) ✗ ./gradlew buildPlugin
...
BUILD SUCCESSFUL in 24s
11 actionable tasks: 11 executed
```
4. install plugin from disk - [JetBrains Instruction](https://www.jetbrains.com/help/idea/managing-plugins.html#install_plugin_from_disk)