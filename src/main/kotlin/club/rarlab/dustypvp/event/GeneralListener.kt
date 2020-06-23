package club.rarlab.dustypvp.event

import club.rarlab.dustypvp.config.configurations.BoardOption
import club.rarlab.dustypvp.scoreboard.ScoreboardType
import club.rarlab.dustypvp.scoreboard.supported.InternalScoreboard
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * Class to handle all general events.
 */
class GeneralListener(private val scoreboard: InternalScoreboard) : Listener {
    /**
     * Called when a player joins the server.
     */
    @EventHandler
    fun PlayerJoinEvent.onGeneral() {
        if (!BoardOption.ENABLED.toBoolean()) return
        scoreboard.show(this.player, ScoreboardType.DEFAULT)
    }

    /**
     * Called when a player quits the server.
     */
    @EventHandler
    fun PlayerQuitEvent.onGeneral() {
        if (!BoardOption.ENABLED.toBoolean()) return
        scoreboard.hide(player)
    }
}