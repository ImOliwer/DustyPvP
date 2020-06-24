package club.rarlab.dustypvp.scoreboard.supported

import club.rarlab.dustypvp.config.configurations.BoardOption.*
import club.rarlab.dustypvp.core.Registration.isPlaceholderApi
import club.rarlab.dustypvp.scoreboard.BaseScoreboard
import club.rarlab.dustypvp.scoreboard.ScoreboardType
import club.rarlab.dustypvp.scoreboard.teams.TeamsScoreboard
import club.rarlab.dustypvp.util.base.reversedIf
import club.rarlab.dustypvp.util.color
import me.clip.placeholderapi.PlaceholderAPI.setPlaceholders
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

/**
 * Class to handle the internal Scoreboard.
 */
class InternalScoreboard : BaseScoreboard {
    /**
     * [MutableMap] of players with toggled scoreboards.
     */
    val toggled: MutableMap<Player, TeamsScoreboard> = IdentityHashMap()

    /**
     * Show a specific [ScoreboardType] to a [Player].
     */
    override fun show(player: Player, board: ScoreboardType) {
        val editable = toggled.compute(player) { _, scoreboard ->
            return@compute scoreboard ?: TeamsScoreboard()
        }

        editable?.run {
            this.type = board
            this.showTo(player)
        }
    }

    /**
     * Hide a [Player]'s Scoreboard.
     */
    override fun hide(player: Player) {
        if (toggled.remove(player) != null) {
            player.scoreboard = Bukkit.getScoreboardManager()!!.newScoreboard
        }
    }

    /**
     * Update a [Player]'s Scoreboard.
     */
    override fun update(player: Player) {
        val editable = toggled[player] ?: return
        this.refresh(player, editable)
    }

    /**
     * Get the [ScoreboardType] that a [Player] has toggled, null if none.
     */
    override fun getToggled(player: Player): ScoreboardType? {
        TODO("Not yet implemented")
    }

    /**
     * Refresh a player's Scoreboard.
     */
    private fun refresh(player: Player, board: TeamsScoreboard) {
        with (board) { when (board.type) {
            ScoreboardType.DEFAULT -> {
                title(TITLE.toString().color())

                val isCustomScore = CUSTOM_SCORE_ENABLED.toBoolean()

                LINES.toArray<String>().reversedIf(!isCustomScore).forEachIndexed { index, line ->
                    val processed = if (isPlaceholderApi) setPlaceholders(player, line) else line
                    line(index, processed, if (isCustomScore) CUSTOM_SCORE.toInt() else index)
                }
            }
        }}
    }
}