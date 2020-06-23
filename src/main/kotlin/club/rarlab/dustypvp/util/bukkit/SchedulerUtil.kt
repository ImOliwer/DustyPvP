package club.rarlab.dustypvp.util.bukkit

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Object with scheduler utility methods.
 */
object SchedulerUtil {
    /**
     * [ExecutorService] instance.
     */
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    /**
     * Run an operation asynchronously.
     */
    fun callAsync(action: () -> Unit) = executor.execute(action)
}