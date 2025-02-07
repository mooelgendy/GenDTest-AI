package com.gendtest.ai.action

import com.gendtest.ai.exception.LowMemoryException
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiManager
import com.gendtest.ai.service.OllamaService
import com.gendtest.ai.service.TestGenerator
import com.gendtest.ai.ui.TestConfigDialog
import java.nio.charset.StandardCharsets

class GenerateTestAction : AnAction("Generate Tests with GenDTest AI") {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val psiFile = PsiManager.getInstance(project).findFile(virtualFile) as? PsiJavaFile ?: return
        val psiClass = psiFile.classes.firstOrNull() ?: return

        val dialog = TestConfigDialog(project)
        if (dialog.showAndGet()) {
            ProgressManager.getInstance().run(object : Task.Backgroundable(project, "Generating Tests...") {
                override fun run(indicator: com.intellij.openapi.progress.ProgressIndicator) {
                    try {
                        val config = dialog.getConfig()
                        val prompt = TestGenerator().buildPrompt(psiClass, config.testType, config.scenarios)
                        val testCode = OllamaService().generateTest(prompt)

                        WriteCommandAction.runWriteCommandAction(project) {
                            createTestFile(project, psiClass, testCode)
                        }
                    } catch (e: LowMemoryException) {
                        throw e
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            })
        }
    }

    private fun createTestFile(project: Project, psiClass: PsiClass, code: String) {
        val packageName = (psiClass.containingFile as PsiJavaFile).packageName
        val baseDir = project.projectFile?.parent ?: return

        // New 2024.1 API for test sources detection
        val testRoot = ProjectRootManager.getInstance(project)
            .contentSourceRoots
            .firstOrNull { it.path.contains("src/test/java") }
            ?: baseDir.findChild("src")?.findChild("test")?.findChild("java")
            ?: run {
                VfsUtil.createDirectoryIfMissing(baseDir, "src/test/java")
            }

        val testFile = testRoot?.createChildData(this, "${psiClass.name}Test.java")
        testFile?.setBinaryContent("""
        package $packageName;
        
        $code
    """.trimIndent().toByteArray(StandardCharsets.UTF_8))
    }
}