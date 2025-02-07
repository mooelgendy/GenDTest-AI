package com.gendtest.ai.model

import com.gendtest.ai.utils.TestType

data class TestConfig(
    val testType: TestType,
    val scenarios: List<String> = emptyList()
)
