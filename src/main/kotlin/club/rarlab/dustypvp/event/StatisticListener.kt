package club.rarlab.dustypvp.event

import club.rarlab.dustypvp.structure.PlayerHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

/**
 * Class with all statistics related events.
 */
class StatisticListener : Listener {
    /**
     * Kills / Deaths are being handled in this event.
     */
    @EventHandler
    fun PlayerDeathEvent.onDeath() {
        PlayerHandler.fetch(this.entity)?.run {
            this.deaths++
            this.killStreak = 0
        }

        val killer = this.entity.killer ?: return

        PlayerHandler.fetch(killer)?.run {
            this.kills++
            this.killStreak++
        }
    }
}