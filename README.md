![JetBrains Plugin](https://img.shields.io/jetbrains/plugin/v/15673-asyncapi)
[![GitHub stars](https://img.shields.io/github/stars/asyncapi/jasyncapi-idea-plugin?style=social)](https://github.com/asyncapi/jasyncapi-idea-plugin/stargazers)

## Plugin went Freemium model

Plugin was moved to a freemium model. Here is [announce](https://pavelon.dev/posts/asyncapi-jetbrains-plugin-update-freemium/) where you can
get more details:
- 🔧 Why the switch?
- 💡 What's still free?
- ✅ What's coming next?

# AsyncAPI for IntelliJ IDEA & all JetBrains IDEs — an AsyncAPI studio inside your editor 🚀

Turn IntelliJ IDEA, WebStorm, PyCharm, GoLand, PhpStorm, Rider, RustRover, CLion, and Android Studio into a full [AsyncAPI](https://www.asyncapi.com/) studio: create, validate, autocomplete, preview, and navigate event-driven API specifications without leaving your IDE. Full support for AsyncAPI **2.0.0–2.6.0** and **3.0.0 / 3.1.0**, in YAML and JSON, for Apache Kafka, MQTT, AMQP, WebSockets, NATS, Solace, Redis, and more.

One plugin, two workflows:

*   **AsyncAPI contract authors** — create a contract, explore it, validate it, complete it, preview it, and resolve every `$ref`.
*   **Engineers working with Spring applications** — a Project Overview that discovers message handlers and listeners across every module and links them back to your code.

### 👩‍💻 For AsyncAPI contract authors

#### Create a new AsyncAPI contract — Free

Start a contract from scratch or from a template via _File → New → AsyncAPI_, in YAML or JSON, for AsyncAPI 2.6.0 or 3.0.0. Referenced files are recognised automatically and get the AsyncAPI file icon.

#### Explore a contract in the editor — Free

Open any AsyncAPI document and browse it as a structured tree: servers, channels, operations, messages, schemas, and bindings, with colored nodes, grouped detail views, and click-to-navigate between them. Read-only exploration is free; switching the editor into edit mode is a Pro feature (see below).

#### Validate a contract — Free

Real-time validation against dedicated, up-to-date AsyncAPI JSON Schemas for 2.x and 3.x, with inline error highlighting and quick fixes as you type.

#### Complete a contract — Free

Context-aware completion for channels, operations, messages, bindings, components, security schemes, and enum values, driven by the same schemas used for validation.

#### Preview a full contract — Free

Render a whole AsyncAPI document instantly — in a split pane inside the IDE or in your browser — for AsyncAPI 2.6.0, 3.0.0, and 3.1.0, with local, file, and remote references dereferenced (subject to the remote-host limit below).

#### Resolve references — Free, with Pro extras

A single engine resolves every `$ref` — local pointers, file references, and remote `http` / `https` references — the same way in the editor and in the preview.

*   **Reference completion, local and remote — Free.** Completion is offered for every kind of `$ref` as you type it.
*   **Current-folder listing — Free.** While you write a file reference, completion lists the contents of the current folder, so you can find and pick the right `.json` or `.yaml` document without remembering its path. Rename a referenced file and every `$ref` to it updates automatically.
*   **JSON Pointer navigation into local or remote content — Free.** After the `#`, completion offers the elements _inside_ the target document — a local file or a remote URL — so you can point straight at the exact node you need, for example one `server` or one `message`. _Go to Declaration_ (`Ctrl/Cmd+B`) follows the pointer into that document's own content and puts the caret on the element.
*   **One approved remote host — Free.** A remote reference is never fetched until you allow its host. You may keep **one** allowed host on the free tier; **denying** hosts is unlimited and always free. Allowing **more than one** host is a Pro feature. Answers are stored per project and can be reviewed, changed, or removed in _Settings → Tools → AsyncAPI → Remote References_.
*   **HTTP proxy — Pro.** Route remote reference resolution through a configurable proxy.

Also handled: reference chains (a `$ref` that points at another `$ref`), references met part-way along a pointer path, Avro `.avsc` schemas, cross-language JSON↔YAML references, cycle detection, and a dedicated inspection that names each problem — unreachable document, pointer that finds nothing, unsupported fragment, reference cycle, host awaiting a decision — with its own quick fix.

#### Work with AsyncAPI components — Pro

Treat standalone components as first-class files: **create** them from scratch or a template, **validate** them, get **autocompletion** in them, and **preview** them in isolation — for Server, Server Variable, Channel, Channel Parameter, Message, Message Trait, Message Correlation ID, Operation, Operation Trait, Operation Reply, and Operation Reply Address.

#### Edit contracts and components through the UI — Pro

Enable edit mode and change documents and extracted components through a UI form — in both JSON and YAML — with built-in validation that prevents invalid edits.

#### Lint with Spectral or Redocly — Pro

Lint your specifications with [Spectral](https://stoplight.io/open-source/spectral) or [Redocly](https://redocly.com/), in automatic or manual mode, with `.spectralignore` / `.redoclyignore` support.

#### Find every contract and component in the project — Pro

The **Project Overview** tool window indexes every AsyncAPI document and standalone component in the project, filterable by AsyncAPI version and component type, so you can see what your project already describes and jump to any of it in one click — even in large, multi-module repositories.

### 🌱 For engineers working with Spring applications

#### Discover message handlers and listeners with Project Overview — Pro

The **Project Overview** tool window scans every module of your Spring project and maps its messaging endpoints, so you can see your event-driven architecture at a glance instead of searching for annotations by hand. Supported annotations:

*   **Apache Kafka** — `@KafkaListener`, `@KafkaListeners`, `@KafkaHandler`
*   **RabbitMQ (AMQP)** — `@RabbitListener`
*   **Apache Pulsar** — `@PulsarListener`
*   **Amazon SQS** — `@SqsListener`, `@SqsHandler`
*   **Amazon SNS** — `@NotificationMessageMapping`, `@NotificationSubscriptionMapping`, `@NotificationUnsubscribeConfirmationMapping`
*   **JMS** — `@JmsListener`
*   **STOMP** — `@MessageMapping`, `@SubscribeMapping`

Endpoints are attributed to the right module and broker for both Gradle and Maven source sets, searchable by class, method, or Javadoc, and every row links straight to its method or class in the editor. It stays fast on projects with hundreds of listeners.

### What's free / What's Pro

**Free — no account required**

*   Create a new AsyncAPI contract from scratch or a template
*   Explore a contract in the editor in read-only mode (tree, navigation, detail views)
*   Validate a contract and see errors in the editor
*   Complete a contract as you type
*   Preview a full contract (in-IDE and browser)
*   Reference completion — local and remote
*   Current-folder listing while writing a local file reference
*   JSON Pointer navigation into local or remote content
*   One approved remote host; denying hosts is unlimited

**Pro**

*   Create AsyncAPI components from scratch or a template
*   Validate and autocomplete AsyncAPI components
*   Preview AsyncAPI components in isolation
*   Edit AsyncAPI contracts and components through the UI
*   Project Overview — explore Spring messaging, and find contracts and components across the project
*   More than one approved remote host while resolving references
*   HTTP proxy for reference resolution
*   Linting with Spectral or Redocly

**Start a free trial** to unlock the Pro features in seconds — no commitment. A license keeps solo development sustainable and funds what's next.

### Supported protocols, bindings & components

**Protocols** (19): Amazon SNS, Amazon SQS, AMQP 0-9-1, AMQP 1.0, Anypoint MQ, Apache Kafka, Apache Pulsar, Google Cloud Pub/Sub, HTTP, IBM MQ, JMS, Mercure, MQTT, MQTT v5.0, NATS, Redis, Solace, STOMP, WebSockets.

**Bindings**: server, channel, operation, and message bindings for every protocol above.

**Components** (AsyncAPI v3): Server, Server Variable, Channel, Channel Parameter, Message, Message Trait, Message Correlation ID, Operation, Operation Trait, Operation Reply, Operation Reply Address — plus Schemas, Security Schemes, Tags, and External Documentation inside a full contract.

**Schema formats**: AsyncAPI Schema, JSON Schema, OpenAPI 3 Schema, Avro, and XML.

**Specification versions**: AsyncAPI 2.0.0, 2.1.0, 2.2.0, 2.3.0, 2.4.0, 2.5.0, 2.6.0, 3.0.0, and 3.1.0.

### Feedback

Issues and ideas: [GitHub](https://github.com/asyncapi/jasyncapi-idea-plugin/issues).

---

# 📥 Getting Started

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
