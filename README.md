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

## How to choose right version
If you want to build the plugin locally and/or install it from this repo - check the compatibility table:

| Plugin version                                                                                    | Android Studio                                               | AppCode           | Aqua                | CLion             | Code With Me Guest  | DataGrip          | DataSpell              | GoLand            | IntelliJ IDEA Community | IntelliJ IDEA Educational | IntelliJ IDEA Ultimate | JetBrains Client    | MPS                 | PhpStorm                | PyCharm Community | PyCharm Educational | PyCharm Professional | Rider             | RubyMine          | WebStorm          |
|---------------------------------------------------------------------------------------------------|--------------------------------------------------------------|-------------------|---------------------|-------------------|---------------------|-------------------|------------------------|-------------------|-------------------------|---------------------------|------------------------|---------------------|---------------------|-------------------------|-------------------|---------------------|----------------------|-------------------|-------------------|-------------------|
| [1.0.0+idea2021](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/1.0.0%2Bidea2021) | Chipmunk &#124; 2021.2.1 — Chipmunk &#124; 2021.2.1 Patch 2  | 2021.2 — 2021.2.5 | build 211.* — 212.* | 2021.2 — 2021.2.4 | build 211.* — 212.* | 2021.2 — 2021.2.4 | build 211.* — 212.*    | 2021.2 — 2021.2.5 | 2021.2 — 2021.2.4       | 2021.2 — 2021.2.3         | 2021.2 — 2021.2.4      | build 211.* — 212.* | 2021.2 — 2021.2.6   | 2021.2 — 2021.2.4       | 2021.2 — 2021.2.4 | 2021.2 — 2021.2.3   | 2021.2 — 2021.2.4    | 2021.2 — 2021.2.3 | 2021.2 — 2021.2.4 | 2021.2 — 2021.2.4 |
| [1.1.0+idea2021](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/1.1.0%2Bidea2021) | Chipmunk &#124; 2021.2.1 — Chipmunk &#124; 2021.2.1 Patch 2  | 2021.2 — 2021.2.5 | build 211.* — 212.* | 2021.2 — 2021.2.4 | build 211.* — 212.* | 2021.2 — 2021.2.4 | build 211.* — 212.*    | 2021.2 — 2021.2.5 | 2021.2 — 2021.2.4       | 2021.2 — 2021.2.3         | 2021.2 — 2021.2.4      | build 211.* — 212.* | 2021.2 — 2021.2.6   | 2021.2 — 2021.2.4       | 2021.2 — 2021.2.4 | 2021.2 — 2021.2.3   | 2021.2 — 2021.2.4    | 2021.2 — 2021.2.3 | 2021.2 — 2021.2.4 | 2021.2 — 2021.2.4 |
| [1.2.0+idea2021](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/1.2.0%2Bidea2021) | Bumblebee &#124; 2021.1.1 — Chipmunk &#124; 2021.2.1 Patch 2 | 2021.1 — 2021.2.5 | build 211.0 — 212.* | 2021.1 — 2021.2.4 | build 211.0 — 212.* | 2021.1 — 2021.2.4 | build 211.0 — 212.*    | 2021.1 — 2021.2.5 | 2021.1 — 2021.2.4       | 2021.1 — 2021.2.3         | 2021.1 — 2021.2.4      | build 211.0 — 212.* | 2021.1 — 2021.2.6   | 2021.1 — 2021.2.4       | 2021.1 — 2021.2.4 | 2021.1 — 2021.2.3   | 2021.1 — 2021.2.4    | 2021.1 — 2021.2.3 | 2021.1 — 2021.2.4 | 2021.1 — 2021.2.4 |
| [1.3.0+idea2021](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/1.3.0%2Bidea2021) | Bumblebee &#124; 2021.1.1 — Dolphin &#124; 2021.3.1 Patch 1  | 2021.1 — 2021.3.3 | build 211.0 — 213.* | 2021.1 — 2021.3.4 | build 211.0 — 213.* | 2021.1 — 2021.3.4 | 2021.3 — 2021.3.3      | 2021.1 — 2021.3.5 | 2021.1 — 2021.3.3       | 2021.1 — 2021.3.4         | 2021.1 — 2021.3.3      | build 211.0 — 213.* | 2021.1 — 2021.3.2   | 2021.1 — 2021.3.3       | 2021.1 — 2021.3.3 | 2021.1 — 2021.3.4   | 2021.1 — 2021.3.3    | 2021.1 — 2021.3.4 | 2021.1 — 2021.3.3 | 2021.1 — 2021.3.3 |
| [1.4.0+idea2021](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/1.4.0%2Bidea2021) | Bumblebee &#124; 2021.1.1 — Dolphin &#124; 2021.3.1 Patch 1  | 2021.1 — 2021.3.3 | build 211.0 — 213.* | 2021.1 — 2021.3.4 | build 211.0 — 213.* | 2021.1 — 2021.3.4 | 2021.3 — 2021.3.3      | 2021.1 — 2021.3.5 | 2021.1 — 2021.3.3       | 2021.1 — 2021.3.4         | 2021.1 — 2021.3.3      | build 211.0 — 213.* | 2021.1 — 2021.3.2   | 2021.1 — 2021.3.3       | 2021.1 — 2021.3.3 | 2021.1 — 2021.3.4   | 2021.1 — 2021.3.3    | 2021.1 — 2021.3.4 | 2021.1 — 2021.3.3 | 2021.1 — 2021.3.3 |
| [1.5.0+idea2021](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/1.5.0%2Bidea2021) | Bumblebee &#124; 2021.1.1 — Electric Eel &#124; 2022.1.1     | 2021.1 — 2022.1.4 | build 211.0 — 221.* | 2021.1 — 2022.1.3 | build 211.0 — 221.* | 2021.1 — 2022.1.5 | 2021.3 — 2022.1.4      | 2021.1 — 2022.1.4 | 2021.1 — 2022.1.4       | 2021.1 — 2022.1.3         | 2021.1 — 2022.1.4      | build 211.0 — 221.* | 2021.1 — 2021.3.2   | 2021.1 — 2022.1.4       | 2021.1 — 2022.1.4 | 2021.1 — 2022.1.3   | 2021.1 — 2022.1.4    | 2021.1 — 2022.1.2 | 2021.1 — 2022.1.4 | 2021.1 — 2022.1.4 |
| [1.6.0+idea2021](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/1.6.0%2Bidea2021) | Bumblebee &#124; 2021.1.1 — Electric Eel &#124; 2022.1.1     | 2021.1 — 2022.1.4 | build 211.0 — 221.* | 2021.1 — 2022.1.3 | build 211.0 — 221.* | 2021.1 — 2022.1.5 | 2021.3 — 2022.1.4      | 2021.1 — 2022.1.4 | 2021.1 — 2022.1.4       | 2021.1 — 2022.1.3         | 2021.1 — 2022.1.4      | build 211.0 — 221.* | 2021.1 — 2021.3.2   | 2021.1 — 2022.1.4       | 2021.1 — 2022.1.4 | 2021.1 — 2022.1.3   | 2021.1 — 2022.1.4    | 2021.1 — 2022.1.2 | 2021.1 — 2022.1.4 | 2021.1 — 2022.1.4 |
| [1.7.0+idea2021](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/1.7.0%2Bidea2021) | Bumblebee &#124; 2021.1.1 — Flamingo &#124; 2022.2.1 Beta 1  | 2021.1 — 2022.2.5 | build 211.0 — 222.* | 2021.1 — 2022.2.4 | build 211.0 — 222.* | 2021.1 — 2022.2.5 | 2021.3 — 2022.2.4      | 2021.1 — 2022.2.5 | 2021.1 — 2022.2.4       | 2021.1 — 2022.2.2         | 2021.1 — 2022.2.4      | build 211.0 — 222.* | 2021.1 — 2022.2     | 2021.1 — 2022.2.4       | 2021.1 — 2022.2.4 | 2021.1 — 2022.2.2   | 2021.1 — 2022.2.4    | 2021.1 — 2022.2.4 | 2021.1 — 2022.2.4 | 2021.1 — 2022.2.4 |
| [1.7.1+idea2021](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/1.7.1%2Bidea2021) | Bumblebee &#124; 2021.1.1 — Flamingo &#124; 2022.2.1 Beta 1  | 2021.1 — 2022.2.5 | build 211.0 — 222.* | 2021.1 — 2022.2.4 | build 211.0 — 222.* | 2021.1 — 2022.2.5 | 2021.3 — 2022.2.4      | 2021.1 — 2022.2.5 | 2021.1 — 2022.2.4       | 2021.1 — 2022.2.2         | 2021.1 — 2022.2.4      | build 211.0 — 222.* | 2021.1 — 2022.2     | 2021.1 — 2022.2.4       | 2021.1 — 2022.2.4 | 2021.1 — 2022.2.2   | 2021.1 — 2022.2.4    | 2021.1 — 2022.2.4 | 2021.1 — 2022.2.4 | 2021.1 — 2022.2.4 |
| [1.8.0+jre11](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/1.8.0%2Bjre11)       | Bumblebee &#124; 2021.1.1 — Flamingo &#124; 2022.2.1 Beta 1  | 2021.1 — 2022.2.5 | build 211.0 — 222.* | 2021.1 — 2022.2.4 | build 211.0 — 222.* | 2021.1 — 2022.2.5 | 2021.3 — 2022.2.4      | 2021.1 — 2022.2.5 | 2021.1 — 2022.2.4       | 2021.1 — 2022.2.2         | 2021.1 — 2022.2.4      | build 211.0 — 222.* | 2021.1 — 2022.2     | 2021.1 — 2022.2.4       | 2021.1 — 2022.2.4 | 2021.1 — 2022.2.2   | 2021.1 — 2022.2.4    | 2021.1 — 2022.2.4 | 2021.1 — 2022.2.4 | 2021.1 — 2022.2.4 |
| [2.0.0+jre17](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/2.0.0%2Bjre17)       | Giraffe &#124; 2022.3.1 Canary 1                             | 2022.3 — 2022.3.1 | 2022.3 (preview)    | 2022.3 — 2022.3.1 | build 223.0 — 223.* | 2022.3 — 2022.3.3 | 2022.3 — 2022.3.2 (rc) | 2022.3 — 2022.3.1 | 2022.3 — 2022.3.1       | build 223.0 — 223.*       | 2022.3 — 2022.3.1      | build 223.0 — 223.* | build 223.0 — 223.* | 2022.3 — 2022.3.2 (eap) | 2022.3 — 2022.3.1 | build 223.0 — 223.* | 2022.3 — 2022.3.1    | 2022.3 — 2022.3.1 | 2022.3 — 2022.3.1 | 2022.3 — 2022.3.1 |