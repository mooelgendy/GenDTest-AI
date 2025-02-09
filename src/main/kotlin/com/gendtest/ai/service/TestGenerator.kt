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

        return """
        Generate ${testType.displayName} for the following Java class:

        ${psiClass.text}
        
        ### Requirements:
        - Return ONLY the code, including necessary IMPORTS, without explanations.
        - Use JUnit 5 and Java $javaVersion.
        - ${if (testType == TestType.UNIT) "Mock dependencies with Mockito" else "Use '@SpringBootTest'"}
        - Cover those scenarios: ${scenarios.joinToString()}
        - Ensure proper assertions and best practices.
        - Format the output as:
          
          ```java
          // Generated test code here
        """.trimIndent()
    }

    private fun getProjectJavaVersion(module: Module): String {
        val sdk = ModuleRootManager.getInstance(module).sdk
        val versionString = sdk?.versionString ?: "Unknown"
        return versionString.replace(Regex("[^0-9.]"), "").split(".").firstOrNull() ?: "Unknown"
    }
}
