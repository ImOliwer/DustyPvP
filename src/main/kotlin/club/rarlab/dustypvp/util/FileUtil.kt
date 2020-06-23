package club.rarlab.dustypvp.util

import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException

/**
 * Attempt to create a [File] with or without an exception being raised.
 */
fun File.createSafely(print: Boolean = false) {
    try { this.createNewFile() }
    catch (ex: IOException) {
        if (print) ex.printStackTrace()
    }
}

/**
 * Create a file safely with or without resource.
 */
fun createFile(name: String, parent: File, fromResource: Boolean, instance: Plugin): File = File(parent, name).apply {
    if (this.exists()) return this

    if (!parent.exists()) parent.mkdir()
    this.createSafely()

    if (fromResource) {
        instance.getResource(name)?.use {
            it.copyTo(this.outputStream())
        }
    }
}

/**
 * Create a file safely with or without resource.
 */
fun createFile(file: File, fromResource: Boolean, instance: Plugin): File = file.apply {
    if (this.exists()) return this
    val parent: File = file.parentFile

    if (!parent.exists()) parent.mkdir()
    this.createSafely()

    if (fromResource) {
        instance.getResource(name)?.use {
            it.copyTo(this.outputStream())
        }
    }
}