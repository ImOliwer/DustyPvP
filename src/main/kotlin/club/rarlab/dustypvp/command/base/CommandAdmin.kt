package club.rarlab.dustypvp.command.base

import club.rarlab.dustypvp.command.CommandContext
import club.rarlab.dustypvp.command.SubCommand
import club.rarlab.dustypvp.command.admin.CommandBypass
import club.rarlab.dustypvp.config.configurations.Message
import club.rarlab.dustypvp.util.Permission
import club.rarlab.dustypvp.util.buildHelp
import club.rarlab.dustypvp.util.color

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
            sender.sendMessage(buildHelp(subCommands, true, label, sender).color())
            return
        }

        val searched = args[0]
        val subCommand = subCommands.find { it.name.equals(searched, true) }

        if (subCommand == null) {
            sender.sendMessage(Message.COMMAND_INVALID.toString().format(searched).color())
            return
        }

        subCommand.permission?.run {
            if (!sender.hasPermission(this.node)) {
                sender.sendMessage(Message.NO_PERMISSION.toString().color())
                return
            }
        }

        subCommand.execute(CommandContext(sender, label, args.drop(1)))
    }
}