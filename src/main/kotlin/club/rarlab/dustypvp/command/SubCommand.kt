package club.rarlab.dustypvp.command

import club.rarlab.dustypvp.util.Permission

/**
 * Abstract class to handle sub command stuff.
 */
abstract class SubCommand(val name: String, val description: String, val permission: Permission?) {
    /**
     * Execute the sub command.
     */
    abstract fun execute(context: CommandContext)
}