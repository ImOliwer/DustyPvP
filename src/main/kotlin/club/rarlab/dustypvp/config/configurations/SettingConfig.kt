package club.rarlab.dustypvp.config.configurations

import club.rarlab.dustypvp.config.Config
import club.rarlab.dustypvp.config.configurations.Setting.*
import club.rarlab.dustypvp.util.createFile
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

/**
 * Settings implementation of a config.
 */
class SettingConfig(private val plugin: Plugin) : Config {
    /**
     * Set up the configuration file.
     */
    override fun setup(parent: File, fromResource: Boolean): FileConfiguration {
        val createdFile: File = createFile("${getName()}.yml", parent, fromResource, plugin)
        return YamlConfiguration.loadConfiguration(createdFile)
    }

    /**
     * Load options from the configuration.
     */
    override fun load(configuration: FileConfiguration) {
        configuration.run {
            PLACE_BLOCKS.modify(getBoolean("PLACE-BLOCKS", true))
            BREAK_BLOCKS.modify(getBoolean("BREAK-BLOCKS", true))
            LOSE_HUNGER.modify(getBoolean("LOSE-HUNGER", true))
            PLAYER_HURT_ENTITY.modify(getStringList("PLAYER-HURT-ENTITY"))
            ENTITY_HURT_PLAYER.modify(getBoolean("ENTITY-HURT-PLAYER", true))
        }
    }

    /**
     * Get the name of the configuration.
     */
    override fun getName(): String = "settings"
}

/**
 * Enumeration of all settings.
 */
enum class Setting(private var value: Any) {
    PLACE_BLOCKS(true),
    BREAK_BLOCKS(true),
    LOSE_HUNGER(true),
    PLAYER_HURT_ENTITY(listOf<String>()),
    ENTITY_HURT_PLAYER(true);

    /**
     * Get the value as a [String].
     */
    override fun toString(): String = this.value.toString()

    /**
     * Get the value as a [Boolean].
     */
    fun toBoolean(): Boolean = this.toString().toBoolean()

    /**
     * Get the value as a [List].
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> toList(): List<T> = this.value as List<T>

    /**
     * Modify the value.
     */
    fun modify(value: Any) {
        this.value = value
    }
}