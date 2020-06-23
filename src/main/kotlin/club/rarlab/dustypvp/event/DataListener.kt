package club.rarlab.dustypvp.event

import club.rarlab.dustypvp.DustyPlugin
import club.rarlab.dustypvp.scoreboard.ScoreboardType
import club.rarlab.dustypvp.scoreboard.supported.InternalScoreboard
import club.rarlab.dustypvp.scoreboard.teams.TeamsScoreboard
import club.rarlab.dustypvp.structure.PlayerHandler
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * Class with all data related events.
 */
class DataListener : Listener {
    /**
     * Called when a player logs in.
     */
    @EventHandler
    fun PlayerLoginEvent.onLogin() {
        PlayerHandler.supply(this.player.uniqueId)
    }

    /**
     * DEBUGGING ONLY
     */
    @EventHandler
    fun PlayerJoinEvent.onJoin() {
        val plugin = JavaPlugin.getPlugin(DustyPlugin::class.java)
        val scoreboard = InternalScoreboard()

        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            scoreboard.show(player, ScoreboardType.DEFAULT)

            Bukkit.getScheduler().runTaskLater(plugin, Runnable { scoreboard.update(player) {
                (it as TeamsScoreboard).title("this is a new title")
            }}, 20L)

            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                scoreboard.hide(player)
            }, 100L)
        }, 40L)
    }
}