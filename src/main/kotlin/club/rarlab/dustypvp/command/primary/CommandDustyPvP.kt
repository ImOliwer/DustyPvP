package club.rarlab.dustypvp.command.primary

import club.rarlab.dustypvp.command.CommandContext
import club.rarlab.dustypvp.command.SubCommand
import club.rarlab.dustypvp.command.base.CommandAdmin
import club.rarlab.dustypvp.config.configurations.Message.COMMAND_INVALID
import club.rarlab.dustypvp.config.configurations.Message.NO_PERMISSION
import club.rarlab.dustypvp.util.Permission
import club.rarlab.dustypvp.util.buildHelp
import club.rarlab.dustypvp.util.color
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

/**
 * Class to handle the '/dustyadmin' command.
 */
class CommandDustyPvP : CommandExecutor {
    /**
     * [Set] of sub commands.
     */
    private val subCommands: Set<SubCommand> = setOf(CommandAdmin())

    /**
     * Called when the command gets executed.
     */
    override fun onCommand(
            sender: CommandSender,
            command: Command,
            label: String,
            args: Array<out String>
    ): Boolean {
        if (!sender.hasPermission(Permission.COMMAND_BASE.node)) {
            sender.sendMessage(NO_PERMISSION.toString().color())
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage(buildHelp(subCommands, false, label, sender).color())
            return true
        }

        val searched = args[0]
        val subCommand = subCommands.find { it.name.equals(searched, true) }

        if (subCommand == null) {
            sender.sendMessage(COMMAND_INVALID.toString().format(searched).color())
            return true
        }

        subCommand.permission?.run {
            if (!sender.hasPermission(this.node)) {
                sender.sendMessage(NO_PERMISSION.toString().color())
                return true
            }
        }

        subCommand.execute(CommandContext(sender, label, args.drop(1)))
        return true
    }
}