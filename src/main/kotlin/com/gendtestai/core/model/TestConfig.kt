package com.gendtestai.core.model

data class TestConfig(
    val testType: TestType,
    val scenarios: List<String> = emptyList()
)

enum class TestType(val displayName: String) {
    UNIT("Unit Test"),
    INTEGRATION("Integration Test"),
    MOCK("Mock Test")
}