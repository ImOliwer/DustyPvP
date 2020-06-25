package club.rarlab.dustypvp.util.base

import java.util.regex.Pattern

/**
 * Object to hold patterns.
 */
object Patterns {
    val hexColor: Pattern = Pattern.compile("#(\\w{6})")
}