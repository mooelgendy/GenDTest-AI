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
        val methods = psiClass.methods.joinToString("\n") { method ->
            val params = method.parameterList.parameters.joinToString { it.type.canonicalText }
            "Method: ${method.name}\nParameters: $params"
        }

        return """
        Generate ${testType.displayName} for this class:
        ${psiClass.text}
        
        Requirements:
        - Return ONLY THE CODE INCLUDING THE USED LIB IMPORTS without explanations
        - Use JUnit 5 and $javaVersion
        - ${if (testType == TestType.UNIT) "Mock dependencies with Mockito" else "Use @SpringBootTest"}
        - Scenarios: ${scenarios.joinToString()}
        - Format as: ```java\n// code here\n```
    """.trimIndent()
    }

    private fun getProjectJavaVersion(module: Module): String {
        val sdk = ModuleRootManager.getInstance(module).sdk
        val versionString = sdk?.versionString ?: "Unknown"
        return versionString.replace(Regex("[^0-9.]"), "").split(".").firstOrNull() ?: "Unknown"
    }
}
