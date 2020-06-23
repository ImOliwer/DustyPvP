package club.rarlab.dustypvp.util.base

/**
 * Divide the current [Int] by one passed in parameter and
 * return the decimal value.
 */
infix fun Int.decimalDiv(divBy: Int): Float =
    this.toFloat() / divBy.toFloat()