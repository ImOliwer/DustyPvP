package club.rarlab.dustypvp.config.configurations

import club.rarlab.dustypvp.config.Config
import club.rarlab.dustypvp.config.configurations.Message.*
import club.rarlab.dustypvp.util.createFile
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

/**
 * Message implementation of a config.
 */
class MessageConfig(private val plugin: Plugin) : Config {
    /**
     * Set up the configuration file.
     */
    override fun setup(parent: File, fromResource: Boolean): FileConfiguration {
        val createdFile: File = createFile("${getName()}.yml", parent, fromResource, plugin)
        return YamlConfiguration.loadConfiguration(createdFile)
    }

    /**
     * Load messages from the configuration.
     */
    override fun load(configuration: FileConfiguration) {
        configuration.run {
            ENABLED.modify(getString("ENABLED", "enabled")!!)
            DISABLED.modify(getString("DISABLED", "disabled")!!)
            CORRUPT_DATA.modify(getString("CORRUPT-DATA", "&cYour data is corrupt, please log in again!")!!)
            NO_PERMISSION.modify(getString("NO-PERMISSION", "&cInsufficient permission.")!!)
            ONLY_PLAYERS.modify(getString("ONLY-PLAYERS", "This command can only be ran by players.")!!)
            COMMAND_HELP_FORMAT.modify(getString("COMMAND-HELP-FORMAT", "&5 &5\u00bb &d/%s &f- &7%s")!!)
            COMMAND_HELP.modify(getStringList("COMMAND-HELP"))
            COMMAND_INVALID.modify(getString("COMMAND-INVALID", "&5DustyPvP &f\u00bb &dThe command &f%s &dcould not be found!")!!)
            BUILD_DENIED.modify(getString("BUILD-DENIED", "&5DustyPvP &f\u00bb &dYou cannot build here.")!!)
            ADMIN_BYPASS.modify(getString("ADMIN-BYPASS", "&5DustyPvP &f\u00bb &dYour bypass mode has been &f%s&d.")!!)
        }
    }

    /**
     * Get the name of the configuration.
     */
    override fun getName(): String = "messages"
}

/**
 * Enumeration of all messages.
 */
enum class Message(private var value: Any) {
    ENABLED("enabled"),
    DISABLED("disabled"),
    CORRUPT_DATA("&cYour data is corrupt, please log in again!"),
    NO_PERMISSION("&cInsufficient permission."),
    ONLY_PLAYERS("This command can only be ran by players."),
    COMMAND_HELP_FORMAT("&5 &5\u00bb &d/%s &f- &7%s"),
    COMMAND_HELP(listOf(
            "&f&m-------<&5 &lDUSTYPVP &f&m>-------",
            "{commands}",
            "&f&m---------------------------"
    )),
    COMMAND_INVALID("&5DustyPvP &f\u00bb &dThe command &f%s &dcould not be found!"),
    BUILD_DENIED("&5DustyPvP &f\u00bb &dYou cannot build here."),
    ADMIN_BYPASS("&5DustyPvP &f\u00bb &dYour bypass mode has been &f%s&d.");

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
     * Modify the value.
     */
    fun modify(value: Any) {
        this.value = value
    }
}