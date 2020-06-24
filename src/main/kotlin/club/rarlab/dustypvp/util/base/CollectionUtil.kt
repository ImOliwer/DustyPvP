package club.rarlab.dustypvp.util.base

/**
 * Get a random element that matches a specific predicate.
 */
fun <E> Collection<E>.random(predicate: (E) -> Boolean): E? =
        if (this.isNotEmpty()) this.filter(predicate).random() else null

/**
 * Reverse the [Array] if the condition passed is true.
 */
fun <E> Array<E>.reversedIf(condition: Boolean): List<E> =
        if (condition) this.reversed() else this.toList()