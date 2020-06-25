package club.rarlab.dustypvp.util

import club.rarlab.dustypvp.command.SubCommand
import club.rarlab.dustypvp.config.configurations.Message
import club.rarlab.dustypvp.util.base.Patterns
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.ChatColor.translateAlternateColorCodes
import org.bukkit.command.CommandSender

/**
 * Log a colored message to the console.
 */
fun logColored(message: String) = "&7[&5DustyPvP&7] &f$message"
        .sendTo(Bukkit.getConsoleSender())

/**
 * Send a message to a specific [CommandSender].
 */
fun String.sendTo(sender: CommandSender) = sender.sendMessage(
        if (NmsUtil.numberVersion >= 116) this.color().colorRgb() else this.color()
)

/**
 * Process all color codes in a [String].
 */
fun String.color(): String = translateAlternateColorCodes('&', this)

/**
 * Process all RGB color codes in a [String].
 */
fun String.colorRgb(): String {
    val matcher = Patterns.hexColor.matcher(this)

    if (!matcher.find()) return this
    var processed = this

    for (group in 1..matcher.groupCount()) {
        val color = "#${matcher.group(group)}"
        processed = processed.replace(color, ChatColor.of(color).toString())
    }

    return processed
}

/**
 * Build the help message for sub commands.
 */
fun buildHelp(
        subCommands: Set<SubCommand>,
        admin: Boolean,
        label: String,
        sender: CommandSender
): String = Message.COMMAND_HELP.toList().joinToString("\n")
        .replace("{commands}", subCommands.filter {
            it.permission == null || sender.hasPermission(it.permission.node)
        }.joinToString("\n") {
            Message.COMMAND_HELP_FORMAT.toString().format(
                    "$label${if (admin) " admin" else ""} ${it.name}", it.description
            )
        })