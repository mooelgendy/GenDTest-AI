package com.gendtest.ai.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.gendtest.ai.model.TestConfig
import com.gendtest.ai.utils.TestType
import javax.swing.*

class TestConfigDialog(project: Project) : DialogWrapper(project) {
    private val panel = JPanel()

    // Labels for the input fields
    private val testTypeLabel = JLabel("Select Test Type:")
    private val scenariosLabel = JLabel("Scenarios (comma separated):")
    private val modelLabel = JLabel("Ollama Model (default: codellama:7b):")

    // Input components
    private val unitRadio = JRadioButton(TestType.UNIT.displayName)
    private val integrationRadio = JRadioButton(TestType.INTEGRATION.displayName)
    private val scenarioField = JTextField(25)
    private val modelField = JTextField("codellama:7b", 25)

    init {
        title = "Test Generation Configuration"
        init()
    }

    override fun createCenterPanel(): JComponent {
        // Use GroupLayout for a structured layout
        val layout = GroupLayout(panel)
        panel.layout = layout
        layout.autoCreateGaps = true
        layout.autoCreateContainerGaps = true

        // Group the radio buttons in a separate panel for the test type selection
        val testTypePanel = JPanel()
        testTypePanel.add(unitRadio)
        testTypePanel.add(integrationRadio)

        // Define the horizontal and vertical groups
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(testTypeLabel)
                .addComponent(testTypePanel)
                .addComponent(scenariosLabel)
                .addComponent(scenarioField)
                .addComponent(modelLabel)
                .addComponent(modelField)
        )

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(testTypeLabel)
                .addComponent(testTypePanel)
                .addComponent(scenariosLabel)
                .addComponent(scenarioField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(modelLabel)
                .addComponent(modelField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        )

        return panel
    }

    fun getConfig(): TestConfig {
        return TestConfig(
            testType = if (unitRadio.isSelected) TestType.UNIT else TestType.INTEGRATION,
            scenarios = scenarioField.text.split(",").map { it.trim() },
            model = modelField.text
        )
    }
}
