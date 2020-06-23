package club.rarlab.dustypvp.util

/**
 * Enumeration to hold all permissions.
 */
enum class Permission(val node: String) {
    COMMAND_BASE("dustypvp.command"),
    COMMAND_ADMIN("dustypvp.command.admin"),
    COMMAND_BYPASS("dustypvp.command.admin.bypass")
}