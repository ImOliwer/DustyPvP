package club.rarlab.dustypvp.scoreboard

import org.bukkit.entity.Player

/**
 * Interface to handle implementations of Scoreboards.
 */
interface BaseScoreboard {
    /**
     * Show a specific [ScoreboardType] to a [Player].
     */
    fun show(player: Player, board: ScoreboardType)

    /**
     * Hide a [Player]'s Scoreboard.
     */
    fun hide(player: Player)

    /**
     * Update a [Player]'s Scoreboard.
     */
    fun update(player: Player) {
        throw UnsupportedOperationException("Update is not supported here.")
    }

    /**
     * Get the [ScoreboardType] that a [Player] has toggled, null if none.
     */
    fun getToggled(player: Player): ScoreboardType?
}

/**
 * Enumeration to hold all Scoreboard types.
 */
enum class ScoreboardType(val board: String) {
    DEFAULT("default")
}