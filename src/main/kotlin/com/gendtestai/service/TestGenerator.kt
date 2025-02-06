package com.gendtestai.service

import com.intellij.psi.PsiClass
import com.gendtestai.core.model.TestType

class TestGenerator {
    fun buildPrompt(
        psiClass: PsiClass,
        testType: TestType,
        scenarios: List<String>
    ): String {
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
    """.trimIndent()
    }
}