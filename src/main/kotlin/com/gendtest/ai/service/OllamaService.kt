package com.gendtest.ai.service

import com.gendtest.ai.exception.OllamaErrorHandler
import com.gendtest.ai.model.OllamaRequest
import com.google.gson.Gson
import com.intellij.openapi.diagnostic.Logger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.application.ApplicationManager

class OllamaService {
    private val gson = Gson()
    private val client = HttpClient.newHttpClient()
    private val logger = Logger.getInstance(javaClass)

    companion object {
        private const val MIN_RAM = 8_589_934_592L // 8GB in bytes
    }

    private fun extractCodeFromResponse(response: String): String {
        // Match Java code blocks (```java ... ```)
        val codeBlockRegex = """```java\n(.*?)\n```""".toRegex(RegexOption.DOT_MATCHES_ALL)

        return codeBlockRegex.find(response)?.let { match ->
            match.groupValues[1]
                .trimIndent()
                .replace("\\n", "\n")  // Unescape newlines
                .replace("\\\"", "\"") // Unescape quotes
        } ?: run {
            // Fallback: Remove non-code explanations
            response.split("\n")
                .filter { it.trim().startsWith("@Test") || it.trim().startsWith("import") }
                .joinToString("\n")
        }
    }

    fun generateTest(prompt: String): String {
        try {
            // Sanitize prompt to avoid JSON issues
            val sanitizedPrompt = prompt
                .replace("\"", "\\\"") // Escape double quotes
                .replace("\\n", "\n")  // Preserve intended newlines

            val request = OllamaRequest(
                model = "codellama:7b",
                prompt = sanitizedPrompt,
                stream = false
            )

            val requestBody = gson.toJson(request)
            logger.info("Sending to Ollama: $requestBody") // Debug log

            val httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build()

            val response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString())
            val responseBody = parseOllamaResponse(response.body())
            return extractCodeFromResponse(responseBody)
        } catch (e: Exception) {
            logger.error("Ollama request failed", e)
            OllamaErrorHandler.handle(e)
            throw RuntimeException("Test generation failed: ${e.message}")
        }
    }

    private fun parseOllamaResponse(json: String): String {
        return gson.fromJson(json, Map::class.java)["response"] as? String
            ?: throw RuntimeException("Invalid Ollama response: $json")
    }

    private fun showMemoryErrorNotification(message: String) {
        ApplicationManager.getApplication().invokeLater {
            val notification = NotificationGroupManager.getInstance()
                .getNotificationGroup("OllamaMemoryErrors")
                .createNotification(
                    "Ollama Memory Error",
                    "$message. ${getMemoryAdvice()}",
                    NotificationType.ERROR
                )

            Notifications.Bus.notify(notification) // Ensures notification appears as a pop-up
        }
    }

    private fun getMemoryAdvice(): String {
        return """
            Try:
            1. Close other applications
            2. Use a smaller model: ollama pull codellama:7b-q4
            3. Restart Ollama: ollama serve
        """.trimIndent()
    }
}