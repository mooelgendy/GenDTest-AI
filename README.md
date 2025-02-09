# 🧪 GenDTest AI - IntelliJ Plugin for Generating Unit & Integration Tests with Ollama LLM

---

## 📌 Overview

**GenDTest AI** is an **AI-powered IntelliJ plugin** that generates **unit and integration tests** for Java **Spring Boot** projects using **Ollama LLM** locally—without relying on cloud services.

### 🚀 **Key Features**

✅ **One-Click Unit & Integration Test Generation** for Java Spring Boot applications.\
✅ **Fully Local Execution**—No external API calls, ensuring privacy and security.\
✅ **Customizable Test Scenarios**—Define specific test behaviors.\
✅ **Customizable Local AI Models Options**—CodeLlama, DeepSeek or Mistral via Ollama (Default: **codellama:7b**).\
✅ **Popup Notifications in case of any Low Memory Issue Occurred**—Notifications have ordered steps of how to resolve the issue.\
✅ **Mocking & Exception Handling Support**—Generates test cases with `@MockBean`, exception tests, and more.

---

## 🔧 Installation & Setup

### **1️⃣ Install Ollama Locally**

Ollama is required to run the **LLM model locally** for generating tests.

#### **📥 Step 1: Install Ollama**

🔍 **Windows:**\
Download the installer from [Ollama's official site](https://ollama.com/download) and install it.

🔍 **Mac/Linux** (Run this in the terminal):

```sh
curl -fsSL https://ollama.com/install.sh | sh
```

#### **📥 Step 2: Pull the Required Model**

After installing Ollama, download the model that matches your resource capabilities:

```sh
ollama pull codellama:7b
```

#### **📥 Step 3: Start the Ollama Server**

Before using the plugin, ensure Ollama is running:

```sh
ollama serve
```

> This starts the Ollama **API server** at `http://localhost:11434/`.

---

### **2️⃣ Install the IntelliJ Plugin**

You need to install the plugin from **JetBrains Marketplace** or **Manually**.


#### **📥 First Option: JetBrains Marketplace**

1. Open IntelliJ and go to **File → Settings → Plugins**.
2. Search for **GenDTest AI**.
3. Click Install

#### **📥 Second Option: Manually**

1. Download the compiled plugin `.zip` file from **[Releases](https://github.com/mooelgendy/GenDTest-AI/releases)**.
2. Open IntelliJ and go to **File → Settings → Plugins**.
3. Click **⚙️ (Settings Icon) → Install Plugin from Disk...**.
4. Select the downloaded `.zip` file and install it.
5. Restart IntelliJ to apply changes.

---

## 🚀 How to Use GenDTest AI

### **1️⃣ Open Any Java Spring Boot Project in IntelliJ**

1. Open a **Spring Boot project** in IntelliJ.
2. Ensure your project has **JUnit 5** installed.

### **2️⃣ Generate Tests for a Class**

1. **Open a Java class**.
2. Select **"Generate Tests with GenDTest AI"** from the IntelliJ **Code** menu (Located next to **File, Edit, View...**).
3. Choose **Unit test** or **Integration test**
4. The plugin will use **Ollama** to generate the test cases.
5. The generated tests will be added to the `src/test/java/` folder.

### **3️⃣ Customize Test Scenarios**

You can specify different testing scenarios:

- CRUD scenarios
- Exception handling scenarios
- Negative and positive scenarios

Modify the **scenarios** inside the plugin settings to define **custom test behaviors**.

### **4️⃣ Customize Local AI Model**

You can specify different local AI models:

- Codellama:7B
- Codellama:13B
- Deepseek-coder:6.7B

Modify the **model** inside the plugin settings to define a **specific model**.

---

## 🛠 Troubleshooting

### **1️⃣ Plugin Not Showing in IntelliJ?**

✔️ Ensure the plugin is installed in **File → Settings → Plugins**.\
✔️ Restart IntelliJ after installing.

### **2️⃣ Ollama Not Responding?**

✔️ Run `ollama serve` to start the API server.\
✔️ Test with `curl http://localhost:11434/api/generate` \
✔️ If port `11434` is in use, restart your system and try again.

### **3️⃣ Gradle Build Issues?**

✔️ Run: `./gradlew build --refresh-dependencies`.\
✔️ Ensure **JUnit 5** is installed in `build.gradle.kts`:

```kotlin
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}
```

✔️ Rebuild with: `./gradlew clean build`.

---

## 📚 Technologies & Tools Used

- **Java 17+**
- **Spring Boot** for integration tests
- **JUnit 5** for unit testing
- **Gradle** for dependency management
- **IntelliJ Platform SDK** for plugin development
- **Ollama LLM** for test generation

---

## 🤝 Contributing

Want to improve **GenDTest AI**? Clone the repo and submit a **pull request**! 🚀

1. Clone the repo:
   ```sh
   git clone https://github.com/mooelgendy/GenDTest-AI.git
   ```
2. Make changes and commit:
   ```sh
   git add .
   git commit -m "Enhance test generation feature"
   ```
3. Push and create a PR.

---

## 🌟 License

MIT License © 2025. Free to use and modify.

---

## 🌎 Connect with Me

🔗 LinkedIn: [Mohamed ElGendy](https://linkedin.com/in/mooelgendy)
