package club.rarlab.dustypvp.event

import club.rarlab.dustypvp.structure.PlayerHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerLoginEvent

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
}