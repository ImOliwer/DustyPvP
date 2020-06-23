package club.rarlab.dustypvp.integration

import club.rarlab.dustypvp.structure.PlayerHandler
import club.rarlab.dustypvp.util.logColored
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.entity.Player

/**
 * Class to handle PlaceholderAPI integration
 */
class PlaceholderAPIHook : PlaceholderExpansion() {
    /**
     * Get the version.
     */
    override fun getVersion(): String = "0.0.1"

    /**
     * Get the author.
     */
    override fun getAuthor(): String = "RarLab"

    /**
     * Get the identifier.
     */
    override fun getIdentifier(): String = "dustypvp"

    /**
     * Whether or not the expansion can be registered.
     */
    override fun canRegister(): Boolean = true

    /**
     * Attempt to register the expansion.
     */
    override fun register(): Boolean {
        logColored("&fFound PlaceholderAPI, attempting to register expansion...")
        return super.register()
    }

    /**
     * Handle the placeholder request.
     */
    override fun onPlaceholderRequest(player: Player?, param: String): String? {
        if (player == null) return ""
        val dustyPlayer = PlayerHandler.fetch(player) ?: return null

        return when (param.toLowerCase()) {
            "kills" -> dustyPlayer.kills.toString()
            "deaths" -> dustyPlayer.deaths.toString()
            "kdr" -> "%.2f".format(dustyPlayer.getKdr())
            "killstreak" -> dustyPlayer.killStreak.toString()
            else -> null
        }
    }
}