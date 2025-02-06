# 🧪 IntelliJ Plugin for Generating Unit & Integration Tests Using Ollama LLM

[![JetBrains Plugins](https://img.shields.io/jetbrains/plugin/v/12345-ollama-testgen.svg)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)




## 📌 Overview
This **AI-powered IntelliJ plugin** generates **unit and integration tests** for Java Spring Boot projects using **Ollama LLM** running locally, without cloud dependencies.

![Demo](docs/demo.gif)

🚀 **Key Features:**  
✅ **Generates JUnit & Integration Tests** for Java Spring Boot applications.  
✅ **Runs Completely Locally** (No external API calls).  
✅ **Supports Custom Test Scenarios**.  
✅ **Uses Ollama with a 7B model for test generation**.

---

## 🔧 Installation & Setup

### **1️⃣ Install Ollama Locally**
Ollama is required to run the **7B model locally** for generating tests.

#### **📞 Step 1: Install Ollama**
🔍 **Windows**:  
Download the installer from [Ollama's official site](https://ollama.com/download) and install it.

🔍 **Mac/Linux** (Run this in the terminal):
```sh
curl -fsSL https://ollama.com/install.sh | sh
```

#### **📞 Step 2: Pull the Required Model**
After installing Ollama, download the **7B model**:
```sh
ollama pull codellama:7b
```
> ✅ **Note**: The **7B model** can be upgraded to more advanced model for generating Java test code with more instant and accurate results based on your resources capabilities.

#### **📞 Step 3: Start the Ollama Server**
Before using the plugin, make sure Ollama is running:
```sh
ollama serve
```
> This starts the Ollama **API server** at `http://localhost:11434/`.

---

### **2️⃣ Install the IntelliJ Plugin**
You need to install the plugin in IntelliJ **manually** from the ZIP file.

#### **📞 Step 1: Download the Plugin**
Download the compiled plugin `.zip` file from **[Releases](https://github.com/your-repo-link/releases)**.

#### **📞 Step 2: Install in IntelliJ**
1. Open IntelliJ and go to **File → Settings → Plugins**.
2. Click **⚙️ (Settings Icon) → Install Plugin from Disk...**.
3. Select the downloaded `.zip` file and install it.
4. Restart IntelliJ to apply changes.

---

## 🚀 How to Use the Plugin

### **1️⃣ Open Any Java Spring Boot Project in IntelliJ**
1. Open a **Spring Boot project** in IntelliJ.
2. Ensure your project has **JUnit 5** installed.

### **2️⃣ Generate Tests for a Class**
1. **Right-click on a Java class**.
2. Select **"Generate Tests with LLM"** from the context menu.
3. The plugin will use **Ollama** to generate **JUnit test cases**.
4. The generated tests will be added to the `src/test/java/` folder.

### **3️⃣ Customize Test Scenarios**
You can specify different testing scenarios:
- Unit tests (`@Test`)
- Integration tests (`@SpringBootTest`)
- Mocking (`@MockBean`)
- Exception handling tests

Modify the `prompt` inside the plugin settings to define **custom test behaviors**.

---

## 🔍 Testing the Plugin via cURL (Manual API Test)
If you want to manually test **Ollama**'s response, use **cURL**:

```sh
curl -X POST http://localhost:11434/api/generate \
     -H "Content-Type: application/json" \
     -d '{
           "model": "codellama:13b",
           "prompt": "Write a JUnit test for a Calculator class in Java",
           "stream": false
         }'
```
✅ **Expected Output** (Snippet of generated test):
```json
{
  "response": "public class CalculatorTest { @Test void testAddition() { ... } }"
}
```
---

## 🛠 Troubleshooting

### **1️⃣ Plugin Not Showing in IntelliJ?**
✔️ Ensure the plugin is installed in **File → Settings → Plugins**.  
✔️ Restart IntelliJ after installing.

### **2️⃣ Ollama Not Responding?**
✔️ Run `ollama serve` to start the API server.  
✔️ Test with `curl http://localhost:11434/api/generate`.  
✔️ If port `11434` is in use, restart your system and try again.

### **3️⃣ Gradle Build Issues?**
✔️ Run: `./gradlew build --refresh-dependencies`.  
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
- **Ollama LLM** (13B Model) for test generation

---

## 🤝 Contributing
Want to improve the plugin? Fork the repo and submit a **pull request**! 🚀

1. Clone the repo:
   ```sh
   git clone https://github.com/your-repo-link.git
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
📧 Email: [your.email@example.com](mailto:your.email@example.com)  
🔗 LinkedIn: [Your LinkedIn Profile](https://linkedin.com/in/your-profile)  

