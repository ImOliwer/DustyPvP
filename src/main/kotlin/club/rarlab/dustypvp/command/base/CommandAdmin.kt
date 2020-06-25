package club.rarlab.dustypvp.command.base

import club.rarlab.dustypvp.command.CommandContext
import club.rarlab.dustypvp.command.SubCommand
import club.rarlab.dustypvp.command.admin.CommandBypass
import club.rarlab.dustypvp.config.configurations.Message
import club.rarlab.dustypvp.util.Permission
import club.rarlab.dustypvp.util.buildHelp
import club.rarlab.dustypvp.util.sendTo

/**
 * Class to handle the 'admin' command.
 */
class CommandAdmin : SubCommand(
        "admin",
        "Section with all admin commands",
        Permission.COMMAND_ADMIN
) {
    /**
     * [Set] of sub commands.
     */
    private val subCommands: Set<SubCommand> = setOf(CommandBypass())

    /**
     * Execute the sub command.
     */
    override fun execute(context: CommandContext) {
        val args = context.args
        val sender = context.commandSender
        val label = context.label

        if (args.isEmpty()) {
            buildHelp(subCommands, true, label, sender).sendTo(sender)
            return
        }

        val searched = args[0]
        val subCommand = subCommands.find { it.name.equals(searched, true) }

        if (subCommand == null) {
            Message.COMMAND_INVALID.toString().sendTo(sender)
            return
        }

        subCommand.permission?.run {
            if (!sender.hasPermission(this.node)) {
                Message.NO_PERMISSION.toString().sendTo(sender)
                return
            }
        }

        subCommand.execute(CommandContext(sender, label, args.drop(1)))
    }
}