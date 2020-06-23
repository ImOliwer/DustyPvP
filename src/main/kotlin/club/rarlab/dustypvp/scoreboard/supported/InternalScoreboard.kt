package club.rarlab.dustypvp.scoreboard.supported

import club.rarlab.dustypvp.scoreboard.BaseScoreboard
import club.rarlab.dustypvp.scoreboard.ScoreboardType
import club.rarlab.dustypvp.scoreboard.teams.TeamsScoreboard
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
    private val toggled: MutableMap<Player, TeamsScoreboard> = IdentityHashMap()

    /**
     * Show a specific [ScoreboardType] to a [Player].
     */
    override fun show(player: Player, board: ScoreboardType) {
        val editable = toggled.compute(player) { _, scoreboard ->
            return@compute scoreboard ?: TeamsScoreboard()
        }

        editable?.run {
            title("this is a title")
            line("this is line one")
            line("this is line two")
            line("this is line three")
            show(player)
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
    override fun update(player: Player, action: (Any) -> Unit) {
        val editable = toggled[player] ?: return
        action.invoke(editable)
    }

    /**
     * Get the [ScoreboardType] that a [Player] has toggled, null if none.
     */
    override fun getToggled(player: Player): ScoreboardType? {
        TODO("Not yet implemented")
    }
}