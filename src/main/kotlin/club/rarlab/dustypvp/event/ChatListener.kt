package club.rarlab.dustypvp.event

import club.rarlab.dustypvp.config.configurations.ChatOption
import club.rarlab.dustypvp.core.Registration.isPlaceholderApi
import club.rarlab.dustypvp.placeholder.InternalPlaceholders.processPlayer
import club.rarlab.dustypvp.placeholder.InternalPlaceholders.processStatistics
import club.rarlab.dustypvp.structure.PlayerHandler
import club.rarlab.dustypvp.util.color
import me.clip.placeholderapi.PlaceholderAPI.setPlaceholders
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

/**
 * Class with all chat related events.
 */
class ChatListener : Listener {
    /**
     * Internal chat is being handled in this event.
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    fun AsyncPlayerChatEvent.onInternalChat() {
        if (!ChatOption.ENABLED.toBoolean()) return

        this.isCancelled = true
        val dustyPlayer = PlayerHandler.fetch(player) ?: return

        val text = with (ChatOption.FORMAT.toString()) {
            val processed = processPlayer(player, this)
                .replace("{message}", message)

            if (isPlaceholderApi) {
                setPlaceholders(player, processed)
            } else processed
        }.color()

        val component = TextComponent(text).apply {
            val toolTip: List<String> = ChatOption.TOOL_TIP.toList()

            if (toolTip.isNotEmpty()) {
                this.hoverEvent = HoverEvent(SHOW_TEXT, ComponentBuilder(
                    processStatistics(dustyPlayer, if (isPlaceholderApi) {
                        setPlaceholders(player, toolTip.joinToString("\n"))
                    } else toolTip.joinToString("\n"))
                ).create())
            }
        }

        Bukkit.spigot().broadcast(component)
    }
}