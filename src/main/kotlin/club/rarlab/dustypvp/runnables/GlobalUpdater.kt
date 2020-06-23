package club.rarlab.dustypvp.runnables

import club.rarlab.dustypvp.scoreboard.supported.InternalScoreboard

/**
 * Class to handle the Global Updater runnable.
 */
class GlobalUpdater(private val internalScoreboard: InternalScoreboard) : Runnable {
    /**
     * Run the updater.
     */
    override fun run() {
        with (internalScoreboard) {
            toggled.keys.forEach(this::update)
        }
    }
}