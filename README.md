![JetBrains Plugin](https://img.shields.io/jetbrains/plugin/v/15673-asyncapi)
[![GitHub stars](https://img.shields.io/github/stars/asyncapi/jasyncapi-idea-plugin?style=social)](https://github.com/asyncapi/jasyncapi-idea-plugin/stargazers)

## Plugin goes Freemium model

Plugin was moved to a freemium model. Here is [announce](https://pavelon.dev/posts/asyncapi-jetbrains-plugin-update-freemium/) where you can
get more details:
- 🔧 Why the switch?
- 💡 What's still free?
- ✅ What's coming next?

## Getting Started

1. [Install from JetBrains Marketplace](https://plugins.jetbrains.com/plugin/15673-asyncapi)
2. Enable via *Settings → Plugins*
3. Edit any AsyncAPI spec – the plugin activates automatically

## 🚀 AsyncAPI Plugin for IntelliJ-based IDEs

Enhance your AsyncAPI development workflow directly within IntelliJ IDEA, Android Studio, and other JetBrains IDEs.

---

### 📄 What It Does

This plugin transforms your IDE into a powerful editor for AsyncAPI specifications, offering real-time assistance and preview capabilities.

---

### ✅ Key Features

#### 1. Smart File Recognition
Automatically detects `.yaml`, `.yml`, and `.json` files that follow the AsyncAPI schema.

#### 2. Realtime Validation & Inspections
Validates your spec using the official AsyncAPI JSON Schema with live error highlighting and quick fixes.

#### 3. Context-Aware Autocompletion
Get intelligent code completion for channels, messages, bindings, components, and security schemes. References are fully resolved.

#### 4. Generate New Specifications
Create new AsyncAPI specs via *File → New… → AsyncAPI specification*, complete with boilerplate templates.

#### 5. Interactive Preview
Visualize your spec instantly in an HTML preview, either inside the IDE or in your browser.

#### 6. Reference Resolution
Resolve all `$ref` entries, including local and file-based references, without extra configuration.

---

### 🛠 Who It's For

- AsyncAPI spec authors
- Event-driven architecture teams
- Developers working with Kafka, MQTT, AMQP, etc.

---

### 🌟 Why It Matters

- Speeds up development with smart tooling
- Improves spec accuracy through live validation
- Enables confident changes with instant visual feedback

---

### 📥 Getting Started

1. Install from JetBrains Marketplace
2. Enable via *Settings → Plugins*
3. Edit any AsyncAPI spec – the plugin activates automatically

## 🚀 Building From Sources

To build and install the AsyncAPI plugin locally:

### 1. Clone the repository
```sh
git clone https://github.com/asyncapi/jasyncapi-idea-plugin
```

### 2. Move into the project directory
```sh
cd jasyncapi-idea-plugin
```

### 3. Build the plugin
```sh
./gradlew :buildPlugin
```
> ✅ Should complete without errors.

### 4. Verify the plugin
```sh
./gradlew :verifyPlugin
```
> 🕒 Note: This may take a while on first run, as it downloads every IntelliJ version listed in the `runPluginVerifier` section of `build.gradle.kts`.

> ✅ Should complete without errors.

### 5. Install the plugin from disk
Follow the official JetBrains guide:  
🔗 [Installing Plugin from Disk](https://www.jetbrains.com/help/idea/managing-plugins.html#install_plugin_from_disk)

---

## 📌 How to Choose the Right Version

If you're building or installing the plugin manually from this repository,  
please refer to the [Compatibility Table](./COMPATIBILITY.md) to ensure you're using the correct IntelliJ version.