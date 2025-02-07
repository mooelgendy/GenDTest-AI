package com.gendtest.ai.service

import com.gendtest.ai.utils.TestType
import com.intellij.openapi.module.Module
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.psi.PsiClass

class TestGenerator {
    fun buildPrompt(
        psiClass: PsiClass,
        testType: TestType,
        scenarios: List<String>,
        module: Module
    ): String {
        val javaVersion = getProjectJavaVersion(module)
        val compatibilityHint = if (javaVersion == "8") {
            "Ensure the test code is fully compatible with Java 8. Avoid Java 9+ features like `var`, `List.of()`, and `Optional.orElseThrow()`."
        } else {
            "Ensure the test code is compatible with Java $javaVersion."
        }

        val methods = psiClass.methods.joinToString("\n") { method ->
            val params = method.parameterList.parameters.joinToString { it.type.canonicalText }
            "Method: ${method.name}\nParameters: $params"
        }

        return """
        Generate a ${testType.displayName} for this class:
        ${psiClass.text}
        
        Requirements:
        - Return ONLY THE CODE INCLUDING THE USED LIB IMPORTS without explanations
        - Use JUnit 5
        - Format as: ```java\n// code here\n```
        - ${if (testType == TestType.UNIT) "Mock dependencies with Mockito" else "Use @SpringBootTest"}
        - Scenarios: ${scenarios.joinToString()}
        - $compatibilityHint
    """.trimIndent()
    }

    private fun getProjectJavaVersion(module: Module): String {
        val sdk = ModuleRootManager.getInstance(module).sdk
        val versionString = sdk?.versionString ?: "Unknown"
        return versionString.replace(Regex("[^0-9.]"), "").split(".").firstOrNull() ?: "Unknown"
    }
}
