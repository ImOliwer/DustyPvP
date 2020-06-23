package club.rarlab.dustypvp.placeholder

import club.rarlab.dustypvp.structure.DustyPlayer
import club.rarlab.dustypvp.util.color
import org.apache.commons.lang.text.StrSubstitutor
import org.bukkit.OfflinePlayer

/**
 * Enumeration to hold all internal placeholders.
 */
object InternalPlaceholders {
    /**
     * Process all player placeholders.
     */
    fun processPlayer(player: OfflinePlayer, string: String): String = StrSubstitutor(mapOf(
            "uuid" to player.uniqueId.toString(),
            "player" to player.name
    ), "{", "}").replace(string).color()

    /**
     * Process all statistic placeholders.
     */
    fun processStatistics(dustyPlayer: DustyPlayer, string: String): String = StrSubstitutor(mapOf(
            "kills" to dustyPlayer.kills.toString(),
            "deaths" to dustyPlayer.deaths.toString(),
            "kdr" to "%.2f".format(dustyPlayer.getKdr()),
            "killstreak" to dustyPlayer.killStreak.toString()
    ), "{", "}").replace(string).color()
}