package club.rarlab.dustypvp.data.sources

import club.rarlab.dustypvp.core.Dusty.gson
import club.rarlab.dustypvp.data.DataSource
import club.rarlab.dustypvp.data.DataType
import club.rarlab.dustypvp.helper.Callback
import club.rarlab.dustypvp.structure.DustyPlayer
import club.rarlab.dustypvp.structure.PlayerHandler
import club.rarlab.dustypvp.util.logColored
import com.google.gson.reflect.TypeToken
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.FileReader
import java.nio.file.Files

/**
 * "Flat File" implementation of data source.
 */
class FlatSource(private val plugin: Plugin) : DataSource<FlatSource> {
    /**
     * Load all saved data.
     */
    override fun loadData(callBack: Callback<Map<String, DustyPlayer>>): FlatSource {
        logColored("&fLoading players...")

        with (this.dataFile()) {
            if (!this.exists()) return@with

            FileReader(this).use {
                val serialized = it.buffered().readText()
                val players: Map<String, DustyPlayer> = gson.fromJson(serialized,
                    object : TypeToken<Map<String, DustyPlayer>>(){}.type)
                callBack.action(players)
            }
        }
        return this
    }

    /**
     * Save all cached data.
     */
    override fun saveData(notify: Boolean) {
        if (notify) logColored("&fSaving players...")

        with (dataFile().toPath()) {
            val players = PlayerHandler.getPlayers()
            Files.write(this, gson.toJson(players).toByteArray())
        }
    }

    /**
     * Get the [DataSource] type.
     */
    override fun getType(): DataType = DataType.FLAT

    /**
     * Get the data [File].
     */
    private fun dataFile(): File = File(plugin.dataFolder, "userdata.json")
}