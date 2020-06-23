package club.rarlab.dustypvp.util.base

/**
 * Get a random element that matches a specific predicate.
 */
fun <E> Collection<E>.random(predicate: (E) -> Boolean): E? =
        if (this.isNotEmpty()) this.filter(predicate).random() else null