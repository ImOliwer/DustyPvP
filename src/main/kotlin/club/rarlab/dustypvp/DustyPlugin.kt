package club.rarlab.dustypvp

import club.rarlab.dustypvp.config.configurations.*
import club.rarlab.dustypvp.core.Registration
import club.rarlab.dustypvp.data.DataSource
import club.rarlab.dustypvp.helper.Callback
import club.rarlab.dustypvp.structure.PlayerHandler
import club.rarlab.dustypvp.util.color
import club.rarlab.dustypvp.util.connection.fetchCorrespondingSource
import club.rarlab.dustypvp.util.logColored
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * Main class to handle all operations upon start / stop.
 */
class DustyPlugin : JavaPlugin() {
    /**
     * [DataSource] instance.
     */
    private var dataSource: DataSource<*>? = null

    /**
     * Operations ran when plugin loads.
     */
    override fun onLoad() {
        BaseConfig(this).run { load(setup(dataFolder, fromResource = true)) }
        MessageConfig(this).run { load(setup(dataFolder, fromResource = true)) }
        SettingConfig(this).run { load(setup(dataFolder, fromResource = true)) }
    }

    /**
     * Operations ran when plugin enables.
     */
    override fun onEnable() {
        // base logging
        logColored("Bound source to ${BaseOption.SOURCE_TYPE.toString().toLowerCase().capitalize()}.")

        // load all data
        this.dataSource = fetchCorrespondingSource(this@DustyPlugin)
            .loadData(Callback(PlayerHandler::supplyAll)) as DataSource<*>

        // registration
        with (Registration) {
            this.registerIntegrations()
            this.registerListeners(this@DustyPlugin)
            this.registerCommands(this@DustyPlugin)
        }

        // if the server was reloaded and players that were on don't have data, kick and ask to rejoin
        Bukkit.getOnlinePlayers()
            .filterNot(PlayerHandler::hasData)
            .forEach { player ->
                player.kickPlayer(Message.CORRUPT_DATA.toString().color())
            }

        // start data saving task if enabled
        if (BaseOption.AUTO_SAVE.toBoolean()) {
            val saveTime: Long = 20 * 60 * 5
            Bukkit.getScheduler().runTaskTimer(this, Runnable { dataSource?.saveData(false) }, saveTime, saveTime)
        }
    }

    /**
     * Operations ran when plugin disables.
     */
    override fun onDisable() {
        // save the data and initialize dataSource with null
        dataSource?.saveData(true)
        dataSource = null
    }
}