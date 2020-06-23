package club.rarlab.dustypvp.scoreboard.teams

import club.rarlab.dustypvp.scoreboard.ScoreboardType
import club.rarlab.dustypvp.util.color
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.scoreboard.Team

/**
 * Class to handle Scoreboard of teams.
 */
class TeamsScoreboard {
    private val board: Scoreboard = Bukkit.getScoreboardManager()!!.newScoreboard
    private val objective: Objective = board.registerNewObjective("test", "dummy")
    private val teams = arrayOfNulls<Team>(MAX_LINES)
    var type: ScoreboardType = ScoreboardType.DEFAULT

    /**
     * Set the title.
     */
    fun title(title: Any) { objective.displayName = title.toString().color() }

    /**
     * Add a line with a specific index.
     */
    @JvmOverloads
    fun line(index: Int, text: Any, score: Int = 1): Int {
        val colored = text.toString().color()
        val split = if (colored.length < 16) -1 else if (colored[15] == '§') 15 else 16

        teams[index]!!.prefix = if (split == -1) colored else colored.substring(0, split)
        teams[index]!!.suffix = if (split == -1) "" else colored.substring(split)
        objective.getScore(BLANKS[index]!!).score = score

        return index
    }

    /**
     * Add a line.
     */
    fun line(text: Any): Int {
        for (i in 0 until MAX_LINES) if (teams[i]!!.prefix.isEmpty()) return line(i, text)
        throw NoSuchElementException(ERR_LINE)
    }

    /**
     * Erase a line.
     */
    fun erase(index: Int): Boolean {
        if (index >= MAX_LINES) return false
        board.resetScores(BLANKS[index]!!)
        return true
    }

    /**
     * Erase all lines.
     */
    fun eraseAll() { BLANKS.map { it!! }.forEach(board::resetScores) }

    /**
     * Show the [Scoreboard] to a [Player].
     */
    fun showTo(player: Player) { player.scoreboard = board }

    /**
     * Static stuff.
     */
    companion object {
        const val MAX_LINES = 15
        private const val ERR_LINE = "No empty lines."
        private val BLANKS = arrayOfNulls<String>(MAX_LINES)

        /**
         * Static initialization block.
         */
        init {
            for (i in 0 until MAX_LINES) BLANKS[i] = String(charArrayOf(ChatColor.COLOR_CHAR, ('s'.toInt() + i).toChar()))
        }
    }

    /**
     * Base initialization block.
     */
    init {
        objective.displaySlot = DisplaySlot.SIDEBAR
        for (i in 0 until MAX_LINES) board.registerNewTeam(BLANKS[i]!!)
                .also { teams[i] = it }.addEntry(BLANKS[i]!!)
    }
}