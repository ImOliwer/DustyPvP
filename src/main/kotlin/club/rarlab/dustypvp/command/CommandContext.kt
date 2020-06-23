package club.rarlab.dustypvp.command

import org.bukkit.command.CommandSender

/**
 * Class to hold context of a command that was executed.
 */
data class CommandContext(
        val commandSender: CommandSender,
        val label: String,
        val args: List<String>
)