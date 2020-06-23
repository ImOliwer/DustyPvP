package club.rarlab.dustypvp.config.configurations

import club.rarlab.dustypvp.config.Config
import club.rarlab.dustypvp.config.configurations.BaseOption.*
import club.rarlab.dustypvp.config.configurations.ChatOption.*
import club.rarlab.dustypvp.data.DatabaseConnection
import club.rarlab.dustypvp.data.DatabaseTable
import club.rarlab.dustypvp.util.createFile
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

/**
 * Base implementation of a config.
 */
class BaseConfig(private val plugin: Plugin) : Config {
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
        // data options
        configuration.getConfigurationSection("DataOptions")?.run {
            SOURCE_TYPE.modify(getString("Source", "FLAT")!!)
            AUTO_SAVE.modify(getBoolean("Auto-Save", true))
        }

        // database
        configuration.getConfigurationSection("Database")?.run {
            getConfigurationSection("Authorization")?.run {
                DB_USERNAME.modify(getString("Username", "root")!!)
                DB_PASSWORD.modify(getString("Password", "")!!)
            }

            getConfigurationSection("URL")?.run {
                DB_ADDRESS.modify(getString("Address", "localhost")!!)
                DB_PORT.modify(getInt("Port", 3306))
            }

            getConfigurationSection("Information")?.run {
                DB_DATABASE.modify(getString("Database", "dustypvp")!!)
                DatabaseTable.USERS.modify(getString("Users-Table", "users")!!)
            }
        }

        // internal chat
        configuration.getConfigurationSection("Internal-Chat")?.run {
            ENABLED.modify(getBoolean("Enabled", false))
            FORMAT.modify(getString("Format", "&d{player}&7: &f{message}")!!)
            TOOL_TIP.modify(getStringList("ToolTip"))
        }

        // pool reload
        DatabaseConnection.reload()
    }

    /**
     * Get the name of the configuration.
     */
    override fun getName(): String = "config"
}

/**
 * Enumeration of all base options.
 */
enum class BaseOption(private var value: Any) {
    SOURCE_TYPE("FLAT"),
    AUTO_SAVE(true),
    DB_USERNAME("root"),
    DB_PASSWORD(""),
    DB_ADDRESS("localhost"),
    DB_PORT(3306),
    DB_DATABASE("dustypvp");

    /**
     * Get the value as a [String].
     */
    override fun toString(): String = this.value.toString()

    /**
     * Get the value as an [Int].
     */
    fun toInt(): Int = this.toString().toIntOrNull() ?: 0

    /**
     * Get the value as a [Boolean].
     */
    fun toBoolean(): Boolean = this.toString().toBoolean()

    /**
     * Modify the value.
     */
    fun modify(value: Any) {
        this.value = value
    }
}

/**
 * Enumeration of all chat options.
 */
enum class ChatOption(private var value: Any) {
    ENABLED(false),
    FORMAT("&d{player}&7: &f{message}"),
    TOOL_TIP(listOf(
        "&f* &5Kills: &d{kills}",
        "&f* &5Deaths: &d{deaths}",
        "&f* &5KDR: &d{kdr}"
    ));

    /**
     * Get the value as a [String].
     */
    override fun toString(): String = this.value.toString()

    /**
     * Get the value as a [List].
     */
    @Suppress("UNCHECKED_CAST")
    fun toList(): List<String> = this.value as List<String>

    /**
     * Get the value as a [Boolean].
     */
    fun toBoolean(): Boolean = this.toString().toBoolean()

    /**
     * Modify the value.
     */
    fun modify(value: Any) {
        this.value = value
    }
}