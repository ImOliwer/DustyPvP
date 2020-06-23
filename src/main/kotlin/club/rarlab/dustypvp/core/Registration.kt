package club.rarlab.dustypvp.core

import club.rarlab.dustypvp.command.primary.CommandDustyPvP
import club.rarlab.dustypvp.event.*
import club.rarlab.dustypvp.integration.PlaceholderAPIHook
import club.rarlab.dustypvp.scoreboard.supported.InternalScoreboard
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

/**
 * Object to hold registration methods.
 */
internal object Registration {
    /**
     * [PluginManager] instance.
     */
    private val pluginManager: PluginManager = Bukkit.getPluginManager()

    /**
     * [Boolean] whether or not the PlaceholderAPI integration was successfully registered.
     */
    var isPlaceholderApi: Boolean = false
        private set

    /**
     * Register integrations.
     */
    fun registerIntegrations() {
        val placeholderApi = pluginManager.getPlugin("PlaceholderAPI")
        if (placeholderApi != null && placeholderApi.isEnabled) {
            PlaceholderAPIHook().register()
            this.isPlaceholderApi = true
        }
    }

    /**
     * Register all listeners.
     */
    fun registerListeners(plugin: Plugin, internalScoreboard: InternalScoreboard) {
        with (pluginManager) {
            registerEvents(DataListener(), plugin)
            registerEvents(StatisticListener(), plugin)
            registerEvents(ChatListener(), plugin)
            registerEvents(SettingListener(), plugin)
            registerEvents(GeneralListener(internalScoreboard), plugin)
        }
    }

    /**
     * Register all commands.
     */
    fun registerCommands(plugin: JavaPlugin) {
        with (plugin) {
            getCommand("dustypvp")?.setExecutor(CommandDustyPvP())
        }
    }
}