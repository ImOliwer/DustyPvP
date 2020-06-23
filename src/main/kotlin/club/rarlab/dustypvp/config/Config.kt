package club.rarlab.dustypvp.config

import org.bukkit.configuration.file.FileConfiguration
import java.io.File

/**
 * Interface for configurations.
 */
interface Config {
    /**
     * Set up the configuration file.
     *
     * @param parent       [File] of which the file should be created in.
     * @param fromResource [Boolean] whether or not the file should be copied from the resources folder.
     */
    fun setup(parent: File, fromResource: Boolean): FileConfiguration

    /**
     * Load options from the configuration.
     *
     * @param configuration [FileConfiguration] to load from.
     */
    fun load(configuration: FileConfiguration)

    /**
     * Get the name of the configuration.
     */
    fun getName(): String
}