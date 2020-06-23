package club.rarlab.dustypvp.structure

import club.rarlab.dustypvp.event.custom.DataCreationEvent
import club.rarlab.dustypvp.util.base.decimalDiv
import club.rarlab.dustypvp.util.bukkit.toPlayer
import com.google.gson.annotations.SerializedName
import org.bukkit.Bukkit.getPluginManager
import org.bukkit.entity.Player
import java.util.*

/**
 * Class with all data for a player.
 */
data class DustyPlayer(@SerializedName("uuid") private val _uuid: String) {
    var kills: Int = 0
    var deaths: Int = 0
    @Transient var killStreak: Int = 0
    @Transient var bypass: Boolean = false

    /**
     * Get the player's kdr.
     */
    fun getKdr(): Float = try {
        if (deaths == 0) kills.toFloat() else kills decimalDiv deaths
    } catch (ex: Exception) { 0.00f }

    /**
     * Get the player's [UUID] by string.
     */
    fun getUuid(): UUID = UUID.fromString(_uuid)
}

/**
 * Object with utility methods to modify [DustyPlayer] objects.
 */
object PlayerHandler {
    /**
     * [MutableMap] of all cached players.
     */
    private val players: MutableMap<String, DustyPlayer> = hashMapOf()

    /**
     * Supply the [MutableMap] of players with a new [DustyPlayer] object.
     */
    fun supply(uuid: UUID) = players.compute(uuid.toString()) { key, value ->
        if (value == null) {
            val dustyPlayer = DustyPlayer(key)
            getPluginManager().callEvent(DataCreationEvent(uuid.toPlayer(), dustyPlayer))
            return@compute dustyPlayer
        }
        return@compute value
    }

    /**
     * Supply the [MutableMap] of players with a new [DustyPlayer] object.
     */
    fun supply(dustyPlayer: DustyPlayer) = players.compute(dustyPlayer.getUuid().toString()) { key, value ->
        if (value == null) {
            getPluginManager().callEvent(DataCreationEvent(UUID.fromString(key).toPlayer(), dustyPlayer))
            return@compute dustyPlayer
        }
        return@compute value
    }

    /**
     * Supply the [MutableMap] of players with a map of [DustyPlayer].
     */
    fun supplyAll(toAdd: Map<String, DustyPlayer>) = toAdd.values.forEach { this.supply(it) }

    /**
     * Fetch a [DustyPlayer] object by a [UUID].
     */
    fun fetch(uuid: UUID): DustyPlayer? = players[uuid.toString()]

    /**
     * Fetch [DustyPlayer] object by a [Player].
     */
    fun fetch(player: Player): DustyPlayer? = players[player.uniqueId.toString()]

    /**
     * Get whether or not a [Player]'s [DustyPlayer] object is cached by their [UUID].
     */
    fun hasData(uuid: UUID): Boolean = players.contains(uuid.toString())

    /**
     * Get whether or not a [Player]'s [DustyPlayer] object is cached.
     */
    fun hasData(player: Player): Boolean = players.contains(player.uniqueId.toString())

    /**
     * Get a [Map] of cached players.
     */
    fun getPlayers(): Map<String, DustyPlayer> = players.toMap()
}