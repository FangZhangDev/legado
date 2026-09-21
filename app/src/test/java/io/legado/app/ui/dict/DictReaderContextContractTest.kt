package io.legado.app.ui.dict

import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class DictReaderContextContractTest {

    private val root = repositoryRoot()

    @Test
    fun `dictionary rules receive safe reader context variables`() {
        val dialog = file("app/src/main/java/io/legado/app/ui/dict/DictDialog.kt").readText()
        val viewModel = file("app/src/main/java/io/legado/app/ui/dict/DictViewModel.kt").readText()
        val rule = file("app/src/main/java/io/legado/app/data/entities/DictRule.kt").readText()

        listOf(
            "currentBookName",
            "currentBookAuthor",
            "currentChapterTitle",
            "currentChapterIndex",
            "currentChapterNumber",
            "currentChapterPos",
            "currentReadContext"
        ).forEach { key ->
            assertTrue(dialog.contains("\"$key\""))
        }

        assertTrue(dialog.contains("READ_CONTEXT_MAX_CHARS = 5000"))
        assertTrue(dialog.contains("takeLast(READ_CONTEXT_MAX_CHARS)"))
        assertTrue(viewModel.contains("dictRule.search(word, extraParams)"))
        assertTrue(rule.contains("extraParams = extraParams"))
    }

    private fun file(path: String) = File(root, path)

    private fun repositoryRoot(): File {
        val userDirectory = File(requireNotNull(System.getProperty("user.dir"))).absoluteFile
        return generateSequence(userDirectory) { it.parentFile }
            .first { File(it, "app/src/main").isDirectory }
    }
}
