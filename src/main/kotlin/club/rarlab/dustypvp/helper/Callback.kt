package club.rarlab.dustypvp.helper

/**
 * An action to be called when an operation has finished.
 */
data class Callback<T>(val action: (T) -> Unit)