package com.fraggeil.ticketator.core.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.Desktop
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardOpenOption

actual class ShareManager {
    actual fun shareText(text: String) {
        // On Desktop, sharing text generally involves copying it to the clipboard
        val clipboard = java.awt.Toolkit.getDefaultToolkit().systemClipboard
        val selection = java.awt.datatransfer.StringSelection(text)
        clipboard.setContents(selection, null)
    }

    actual suspend fun shareFile(file: ShareFileModel): Result<Unit> {
        return runCatching {
            withContext(Dispatchers.IO) {
                val savedFile = saveFile(file.fileName, file.bytes)
                withContext(Dispatchers.Main) {
                    openFileWithDefaultApplication(savedFile)
                }
            }
        }
    }

    private fun saveFile(name: String, data: ByteArray): File {
        val tempDir = Files.createTempDirectory("shared_files").toFile()
        tempDir.deleteOnExit() // Ensure the temp directory is cleaned up on exit
        val savedFile = File(tempDir, name)
        Files.write(savedFile.toPath(), data, StandardOpenOption.CREATE, StandardOpenOption.WRITE)
        return savedFile
    }

    private fun openFileWithDefaultApplication(file: File) {
        if (Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(file)
            } else {
                throw UnsupportedOperationException("Opening files is not supported on this platform")
            }
        } else {
            throw UnsupportedOperationException("Desktop operations are not supported on this platform")
        }
    }
}

@Composable
actual fun rememberShareManager(): ShareManager {
    return remember { ShareManager() }
}

