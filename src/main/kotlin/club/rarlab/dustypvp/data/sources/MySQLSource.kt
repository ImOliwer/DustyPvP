package club.rarlab.dustypvp.data.sources

import club.rarlab.dustypvp.data.DataSource
import club.rarlab.dustypvp.data.DataType
import club.rarlab.dustypvp.data.DatabaseQueries.FETCH
import club.rarlab.dustypvp.data.DatabaseQueries.TABLE_CHALLENGES
import club.rarlab.dustypvp.data.DatabaseQueries.TABLE_USERS
import club.rarlab.dustypvp.data.DatabaseQueries.UPDATE_USERS
import club.rarlab.dustypvp.data.DatabaseTable
import club.rarlab.dustypvp.helper.Callback
import club.rarlab.dustypvp.placeholder.InternalPlaceholders.processStatistics
import club.rarlab.dustypvp.structure.DustyPlayer
import club.rarlab.dustypvp.structure.PlayerHandler
import club.rarlab.dustypvp.util.connection.queryStatement
import club.rarlab.dustypvp.util.connection.replaceTable
import club.rarlab.dustypvp.util.logColored
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

/**
 * MySQL implementation of data source.
 */
class MySQLSource(private val plugin: Plugin) : DataSource<MySQLSource> {
    /**
     * Load all saved data.
     */
    override fun loadData(callBack: Callback<Map<String, DustyPlayer>>): MySQLSource {
        this.prepare()
        logColored("&fLoading players...")

        queryStatement(true) { statement ->
            statement.executeQuery(FETCH.replaceTable(DatabaseTable.USERS)).use { result ->
                val players: MutableMap<String, DustyPlayer> = hashMapOf()

                while (result.next()) {
                    // column fetching
                    val uuid = result.getString("uuid")
                    val kills = result.getInt("kills")
                    val deaths = result.getInt("deaths")

                    // supplying
                    players[uuid] = DustyPlayer(uuid).apply {
                        this.kills = kills
                        this.deaths = deaths
                    }
                }

                Bukkit.getScheduler().runTask(plugin, Runnable {
                    PlayerHandler.supplyAll(players)
                })
            }
        }

        return this
    }

    /**
     * Save all cached data.
     */
    override fun saveData(notify: Boolean) {
        if (notify) logColored("&fSaving players...")

        val players: Map<String, DustyPlayer> = PlayerHandler.getPlayers()
        if (players.isEmpty()) return

        queryStatement(true) { statement ->
            players.values.forEach { player ->
                statement.addBatch(processStatistics(player, UPDATE_USERS.replaceTable(DatabaseTable.USERS)
                        .replace("{uuid}", "'${player.getUuid()}'")
                ))
            }

            statement.executeLargeBatch()
        }
    }

    /**
     * Prepare the source.
     */
    override fun prepare() {
        queryStatement(commit = true) { statement ->
            statement.addBatch(TABLE_USERS.replaceTable(DatabaseTable.USERS))
            statement.addBatch(TABLE_CHALLENGES.replaceTable(DatabaseTable.CHALLENGES))
            statement.executeBatch()
        }
    }

    /**
     * Load all saved data.
     */
    override fun getType(): DataType = DataType.MYSQL
}