package club.rarlab.dustypvp.util.bukkit

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

/**
 * Get a [Player] object by their [UUID].
 */
fun UUID.toPlayer(): Player? = Bukkit.getPlayer(this)