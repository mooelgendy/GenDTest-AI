package com.gendtest.ai.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.gendtest.ai.model.TestConfig
import com.gendtest.ai.model.TestType
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JRadioButton
import javax.swing.JTextField

class TestConfigDialog(project: Project) : DialogWrapper(project) {
    private val panel = JPanel()
    private val unitRadio = JRadioButton(TestType.UNIT.displayName)
    private val integrationRadio = JRadioButton(TestType.INTEGRATION.displayName)
    private val scenarioField = JTextField(25)

    init {
        title = "Test Generation Configuration"
        init()
    }

    override fun createCenterPanel(): JComponent {
        panel.apply {
            add(unitRadio)
            add(integrationRadio)
            add(scenarioField)
        }
        return panel
    }

    fun getConfig(): TestConfig {
        return TestConfig(
            testType = if (unitRadio.isSelected) TestType.UNIT else TestType.INTEGRATION,
            scenarios = scenarioField.text.split(",").map { it.trim() }
        )
    }
}