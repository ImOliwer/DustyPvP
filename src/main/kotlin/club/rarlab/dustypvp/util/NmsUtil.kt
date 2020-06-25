package club.rarlab.dustypvp.util

import org.bukkit.Bukkit

/**
 * Object to handle Nms utility methods.
 */
object NmsUtil {
    /**
     * [String] current NMS version running.
     */
    private val exactVersion: String = Bukkit
            .getServer()::class.java
            .`package`.name
            .split("\\.".toRegex())[3]

    /**
     * [Int] MMS version as an int.
     */
    val numberVersion: Int = exactVersion
            .replace("_", "")
            .replace("v", "")
            .replace("R\\d+".toRegex(), "")
            .toInt()
}