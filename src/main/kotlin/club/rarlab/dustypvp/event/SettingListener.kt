package club.rarlab.dustypvp.event

import club.rarlab.dustypvp.config.configurations.Message.BUILD_DENIED
import club.rarlab.dustypvp.config.configurations.Setting.*
import club.rarlab.dustypvp.structure.PlayerHandler.fetch
import club.rarlab.dustypvp.util.sendTo
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.EntityType.PLAYER
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.FoodLevelChangeEvent

/**
 * Class with all setting related events.
 */
class SettingListener : Listener {
    /**
     * Block placement is being handled in this event.
     */
    @EventHandler
    fun BlockPlaceEvent.onSetting() {
        if (PLACE_BLOCKS.toBoolean() || fetch(this.player)?.bypass == true) return
        this.isCancelled = true
        BUILD_DENIED.toString().sendTo(this.player)
    }

    /**
     * Block destroying is being handled in this event.
     */
    @EventHandler
    fun BlockBreakEvent.onSetting() {
        if (BREAK_BLOCKS.toBoolean() || fetch(this.player)?.bypass == true) return
        this.isCancelled = true
        BUILD_DENIED.toString().sendTo(this.player)
    }

    /**
     * Hunger loss is being handled in this event.
     */
    @EventHandler
    fun FoodLevelChangeEvent.onSetting() {
        if (LOSE_HUNGER.toBoolean()) return
        this.isCancelled = true
    }

    /**
     * Entity getting damaged by another entity is being handled in this event.
     */
    @EventHandler
    fun EntityDamageByEntityEvent.onSetting() {
        if (this.entityType == PLAYER && this.damager.type != PLAYER && !ENTITY_HURT_PLAYER.toBoolean()) {
            this.isCancelled = true
            return
        }

        if (this.entityType != PLAYER && this.damager.type == PLAYER && !canHitEntity(this.entityType)) {
            this.isCancelled = true
        }
    }

    /**
     * Get whether or not an [Entity] can be hit.
     */
    private fun canHitEntity(entity: EntityType): Boolean = PLAYER_HURT_ENTITY.toList<String>()
            .none { it == "*" || it.equals(entity.name, true) }
}