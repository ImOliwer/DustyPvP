package club.rarlab.dustypvp.command.primary

import club.rarlab.dustypvp.command.CommandContext
import club.rarlab.dustypvp.command.SubCommand
import club.rarlab.dustypvp.command.base.CommandAdmin
import club.rarlab.dustypvp.config.configurations.Message.COMMAND_INVALID
import club.rarlab.dustypvp.config.configurations.Message.NO_PERMISSION
import club.rarlab.dustypvp.util.Permission
import club.rarlab.dustypvp.util.buildHelp
import club.rarlab.dustypvp.util.sendTo
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
            NO_PERMISSION.toString().sendTo(sender)
            return true
        }

        if (args.isEmpty()) {
            buildHelp(subCommands, false, label, sender).sendTo(sender)
            return true
        }

        val searched = args[0]
        val subCommand = subCommands.find { it.name.equals(searched, true) }

        if (subCommand == null) {
            COMMAND_INVALID.toString().format(searched).sendTo(sender)
            return true
        }

        subCommand.permission?.run {
            if (!sender.hasPermission(this.node)) {
                NO_PERMISSION.toString().sendTo(sender)
                return true
            }
        }

        subCommand.execute(CommandContext(sender, label, args.drop(1)))
        return true
    }
}