package com.gendtest.ai.exception

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.application.ApplicationManager

object OllamaErrorHandler {

    fun handle(error: Throwable) {
        when {
            isMemoryError(error) -> showMemoryError()
            isConnectionError(error) -> showConnectionError()
            isModelNotFound(error) -> showModelError()
            else -> showGenericError(error)
        }
    }

    private fun isMemoryError(error: Throwable): Boolean {
        return error.message?.contains("model requires more system memory", ignoreCase = true) == true
    }

    private fun isConnectionError(error: Throwable): Boolean {
        return error is java.net.ConnectException ||
                error.message?.contains("connection refused", ignoreCase = true) == true
    }

    private fun isModelNotFound(error: Throwable): Boolean {
        return error.message?.contains("model not found", ignoreCase = true) == true
    }

    private fun showMemoryError() {
        val notification = Notification(
            "GenDTest AI Memory",
            "Insufficient Memory",
            """
            GenDTest AI needs more RAM to run. Try:
            1. Close other applications
            2. Use a smaller model: ollama pull codellama:7b-q4
            3. Restart Ollama: ollama serve
            """.trimIndent(),
            NotificationType.ERROR
        )
        showNotification(notification)
    }

    private fun showConnectionError() {
        val notification = Notification(
            "GenDTest AI Connection",
            "Ollama Not Running",
            """
            Could not connect to Ollama. Ensure:
            1. Ollama is installed
            2. Run: ollama serve
            3. Check firewall settings
            """.trimIndent(),
            NotificationType.ERROR
        )
        showNotification(notification)
    }

    private fun showModelError() {
        val notification = Notification(
            "GenDTest AI Model",
            "Model Not Found",
            """
            The requested model is not available. Try:
            1. Pull the model: ollama pull codellama:7b
            2. Check model name in settings
            """.trimIndent(),
            NotificationType.WARNING
        )
        showNotification(notification)
    }

    private fun showGenericError(error: Throwable) {
        val notification = Notification(
            "GenDTest AI Error",
            "Unexpected Error",
            """
            An error occurred: ${error.message}
            Please report this issue.
            """.trimIndent(),
            NotificationType.ERROR
        )
        showNotification(notification)
    }

    private fun showNotification(notification: Notification) {
        ApplicationManager.getApplication().invokeLater {
            Notifications.Bus.notify(notification)
        }
    }
}