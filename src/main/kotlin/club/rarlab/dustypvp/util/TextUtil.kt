package club.rarlab.dustypvp.util

import club.rarlab.dustypvp.command.SubCommand
import club.rarlab.dustypvp.config.configurations.Message
import org.bukkit.Bukkit
import org.bukkit.ChatColor.translateAlternateColorCodes
import org.bukkit.command.CommandSender

/**
 * Log a colored message to the console.
 */
fun logColored(message: String) = Bukkit.getConsoleSender()
    .sendMessage("&7[&5DustyPvP&7] &f$message".color())

/**
 * Process all color codes in a [String].
 */
fun String.color(): String = translateAlternateColorCodes('&', this)

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