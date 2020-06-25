package club.rarlab.dustypvp.helper

import club.rarlab.dustypvp.helper.ActionType.*
import club.rarlab.dustypvp.placeholder.InternalPlaceholders.processPlayer
import club.rarlab.dustypvp.util.color
import club.rarlab.dustypvp.util.sendTo
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Class to hold data on an [Action].
 */
data class Action(private val raw: String) {
    /**
     * Get the exact action.
     */
    fun get(type: ActionType?): String = when (type) {
        BROADCAST -> raw.substring(12)
        CONSOLE, MESSAGE -> raw.substring(10)
        else -> raw
    }

    /**
     * Get the type of [Action].
     */
    fun type(): ActionType? = values().find {
        raw.startsWith("[${it.holder}] ", ignoreCase = true)
    }

    /**
     * Execute the [Action] if the type is not null.
     */
    fun execute(player: Player) {
        val type: ActionType = type() ?: return
        val splitRaw: String = get(type)

        when (type) {
            BROADCAST -> Bukkit.broadcastMessage(processPlayer(player, splitRaw.color()))
            CONSOLE -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), processPlayer(player, splitRaw))
            MESSAGE -> splitRaw.sendTo(player)
        }
    }
}

/**
 * Enumeration of [Action] types.
 */
enum class ActionType(val holder: String) {
    BROADCAST("broadcast"),
    CONSOLE("console"),
    MESSAGE("message")
}