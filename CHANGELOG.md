# Changelog

## 2.7.0+jre21
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/643494)
- [GitHub](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/new?tag=2.7.0%2Bjre17)

### Added

- Resolve local references for correct AsyncAPI specification rendering

### Fixed

- Inject AsyncAPI specification directly into preview instead of saving as temporal file and rendering it

## 2.6.0+jre21
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/edit/versions/stable/596939)
- [GitHub](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/new?tag=2.6.0%2Bjre17)

### Added

- IDEA 2024.2
- Yaml single quoted references handling - '#/components/messages/welcomeMessage'
- `.yml` file recognition

## 2.5.0+jre17
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/520779)
- [GitHub](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/2.5.0%2Bjre17)

### Added

- IDEA 2024.1

### Fixed

- Incorrect validation of Multi Format Schema and bindings

### Changed
- 2.0.0 schema validation and completion:
  - Updated allowed extension name: `^x-[\w\d\-\_]+$` was changed to `^x-[\w\d\.\x2d_]+$`
  - OpenAPI schema v3
  - Added `schemaFormat` to messages to recognize properly non AsyncAPI Schemas
  - Was updated `JsonSchema` Draft 07
- 2.1.0 schema validation and completion:
  - OpenAPI schema v3
  - Added `schemaFormat` to messages to recognize properly non AsyncAPI Schemas
  - Was updated `JsonSchema` Draft 07
- 2.2.0 schema validation and completion:
  - OpenAPI schema v3
  - Added `schemaFormat` to messages to recognize properly non AsyncAPI Schemas
  - Was updated `JsonSchema` Draft 07
- 2.3.0 schema validation and completion:
  - OpenAPI schema v3
  - Added `schemaFormat` to messages to recognize properly non AsyncAPI Schemas
  - Was updated `JsonSchema` Draft 07
- 2.4.0 schema validation and completion:
  - OpenAPI schema v3
  - Added `schemaFormat` to messages to recognize properly non AsyncAPI Schemas
  - Was updated `JsonSchema` Draft 07
- 2.5.0 schema validation and completion:
  - OpenAPI schema v3
  - Added `schemaFormat` to messages to recognize properly non AsyncAPI Schemas
  - Was updated `JsonSchema` Draft 07
- 3.0.0 schema validation and completion:
  - Bindings:
    - Solace:
      - server binding 0.4.0
      - operation binding 0.4.0
    - HTTP:
      - message binding 0.3.0
      - operation binding 0.3.0

## 2.4.0+jre17
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/454611)
- [GitHub](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/2.4.0%2Bjre17)

### Added
- AsyncAPI 3.0.0
- Compatibility with IDEA 2023.3

## 2.3.0+jre17
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/386402)
- [GitHub](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/2.3.0%2Bjre17)

### Added
- AsyncAPI 2.6.0

### Changed
- Plugin description
- New icons

## 2.2.0+jre17
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/371376)
- [GitHub](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/2.2.0%2Bjre17)

### Added
- Compatibility with IDEA 2023.2*

## 2.1.0+jre17
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/329996)
- [GitHub](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/2.1.0%2Bjre17)

### Added
- Compatibility with IDEA 2023.1*

## 2.0.0+jre17
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/282568)
- [GitHub](https://github.com/asyncapi/jasyncapi-idea-plugin/releases/tag/2.0.0%2Bjre17)

### Added
- Compatibility with IDEA 2022.3, 2022.3.*

## 1.8.0+jre11
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/282007)
- [GitHub](https://github.com/Pakisan/jasyncapi-idea-plugin/releases/tag/1.8.0%2Bjre11)

### Added
- Compatibility with IDEA 2022.2.4

### Changed
- was changed `org.jetbrains.intellij` version
  - 1.9.0 -> 1.12.0
- was changed `org.junit.jupiter` version
  - 5.9.0 -> 5.9.1
- was changed `intellij-plugin-verifier` version
  - 1.284 -> 1.289
- was changed Kotlin version
  - 1.6.20 -> 1.8.0

## 1.7.1+idea2021
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/240272)
- [GitHub](https://github.com/Pakisan/jasyncapi-idea-plugin/releases/tag/1.7.1%2Bidea2021)

### Fixed
- Preview in Windows

## 1.7.0+idea2021
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/197680)
- [GitHub](https://github.com/Pakisan/jasyncapi-idea-plugin/releases/tag/1.6.0%2Bidea2021)

### Added
- Compatability with IDEA 2022.2
- Compatability with IDEA 2022.2.1
- Compatability with IDEA 2022.2.2
- Compatability with IDEA 2022.2.3
- AsyncAPI 2.5.0
- Reference to Avro Schemas

### Changed
- was changed `org.jetbrains.intellij` version
  - 1.7.0 -> 1.9.0
- was changed `org.junit.jupiter` version
  - 5.8.2 -> 5.9.0

## 1.6.0+idea2021
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/197680)
- [GitHub](https://github.com/Pakisan/jasyncapi-idea-plugin/releases/tag/1.6.0%2Bidea2021)

### Added
- Compatability with IDEA 2022.1.3
- Compatability with IDEA 2022.1.4
- AsyncAPI Schema auto-completion
- AsyncAPI 2.4.0

### Changed
- was changed `org.jetbrains.intellij` version
  - 1.6.0 -> 1.7.0
- was changed `intellij-plugin-verifier` version
  - 1.278 -> 1.284

## 1.5.0+idea2021
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/168724)
- [GitHub](https://github.com/Pakisan/jasyncapi-idea-plugin/releases/tag/1.5.0%2Bidea2021)

### Added
- Compatability with IDEA 2022.1

### Changed
- was changed `org.jetbrains.intellij` version
  - 1.4.0 -> 1.5.3
- was changed `intellij-plugin-verifier` version
  - 1.268 -> 1.278
- was changed `Gradle` version
  - 6.8 -> 7.4.2
- was changed `JUnit` version
  - 4.12 -> 5.8.2


## 1.4.0+idea2021
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/167090)
- [GitHub](https://github.com/Pakisan/jasyncapi-idea-plugin/releases/tag/1.4.0%2Bidea2021)

### Added
- Compatability with AsyncAPI 2.1.0
- Compatability with AsyncAPI 2.2.0
- Compatability with AsyncAPI 2.3.0


## 1.1.0+idea2021
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/142249)
- [GitHub](https://github.com/Pakisan/jasyncapi-idea-plugin/releases/tag/1.1.0%2Bidea2021)

### Added
- Preview of AsyncAPI schema as html in built-in/external browser
  - Known limitations: reload on save doesn't work


## 1.0.0+idea2021
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/stable/131679)
- [GitHub](https://github.com/Pakisan/jasyncapi-idea-plugin/releases/tag/1.0.0%2Bidea2021)

### Added
- Compatability with IDEA 2021.1
- Compatability with IDEA 2021.2

### Changed
- was changed the way to get the schema
  - AsyncAPIJsonSchemaInspection uses ResourceUtil.getResource(ClassLoader, String, String) instead of deprecated ResourceUtil.getResource(Class, String, String)
  - AsyncAPIYamlSchemaInspection uses ResourceUtil.getResource(ClassLoader, String, String) instead of deprecated ResourceUtil.getResource(Class, String, String)
- was changed icon
  - asyncapi.png was replaced with asyncapi.svg
  - deprecated method IconLoader.getIcon(String) was replaced with IconLoader.getIcon(String, Class)
- was changed `org.jetbrains.intellij` version
  - 0.6.5 -> 1.0

### Fixed
- Was fixed references indexing
  - Was fixed insertion of references to schemas inside index. Before fix, I stored them under reference type. It was causing of wrong schema reference recognition with wrong file icon.
  - Was fixed recognition of referenced files inside of schema. Now referenced files gets asyncapi icon, but only after main schema indexing.
  - Was fixed recognition of referenced files inside of schema. Now referenced files always gets asyncapi icon.


## 1.0.0-EAP-1+idea2021
published to:
- [GitHub](https://github.com/Pakisan/jasyncapi-idea-plugin/releases/tag/1.0.0-EAP-1%2Bidea2021)

### Changed
- was changed `org.jetbrains.intellij` version
  - 0.6.5 -> 1.0

### Fixed
- was fixed references indexing
  - Was fixed insertion of references to schemas inside index. Before fix, I stored them under reference type. It was causing of wrong schema reference recognition with wrong file icon.
  - Was fixed recognition of referenced files inside of schema. Now referenced files gets asyncapi icon, but only after main schema indexing.
  - Was fixed recognition of referenced files inside of schema. Now referenced files always gets asyncapi icon.


## 1.0.0-EAP-1+idea2021-snapshot
published to:
- [GitHub](https://github.com/Pakisan/jasyncapi-idea-plugin/releases/tag/1.0.0-EAP-1%2Bidea2021-snapshot)

### Added
- Compatability with IDEA 2021.1
- Compatability with IDEA 2021.2

### Changed
- was changed icon
  - asyncapi.png was replaced with asyncapi.svg
  - deprecated method IconLoader.getIcon(String) was replaced with IconLoader.getIcon(String, Class)
- was changed the way to get the schema
  - AsyncAPIJsonSchemaInspection uses ResourceUtil.getResource(ClassLoader, String, String) instead of deprecated ResourceUtil.getResource(Class, String, String)
  - AsyncAPIYamlSchemaInspection uses ResourceUtil.getResource(ClassLoader, String, String) instead of deprecated ResourceUtil.getResource(Class, String, String)


## 1.0.0-EAP-1+idea2020
published to:
- [GitHub](https://github.com/Pakisan/jasyncapi-idea-plugin/releases/tag/1.0.0-EAP-1%2Bidea2020)

### Changed
- was changed plugin version - added +2020 to mark that is release for Idea 2020.*


## 1.0.0-EAP-1
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/eap/107189)

### Added
- Compatability with IDEA 2020.1
- Compatability with IDEA 2020.3


## 1.0.0-EAP
published to:
- [JetBrains marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi/versions/EAP/106280)

### Added
- Recognition of AsyncAPI schemas.
- AsyncAPI schema example creation from `file -> new -> AsyncAPI schema`.
- AsyncAPI schema inspection and validation using AsyncAPI JSON Schema.
- Local references resolving with auto-completion in AsyncAPI schema (json).
- File references resolving with auto-completion in AsyncAPI schema (json).
- Local references resolving with auto-completion in AsyncAPI schema (yaml).
- File references resolving with auto-completion in AsyncAPI schema (yaml).