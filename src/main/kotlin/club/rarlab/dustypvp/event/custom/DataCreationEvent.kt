package club.rarlab.dustypvp.event.custom

import club.rarlab.dustypvp.structure.DustyPlayer
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Class to handle the [Event] that gets called when a [Player]
 * has their data being created.
 */
class DataCreationEvent(val player: Player?, val dustyPlayer: DustyPlayer) : Event(false) {
    /**
     * Get the handlers.
     */
    override fun getHandlers(): HandlerList = handlerList

    /**
     * Static stuff.
     */
    companion object {
        /**
         * [HandlerList] instance.
         */
        @JvmStatic
        val handlerList: HandlerList = HandlerList()
    }
}