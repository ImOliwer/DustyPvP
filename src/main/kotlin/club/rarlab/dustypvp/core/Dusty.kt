package club.rarlab.dustypvp.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Object to hold all instances.
 */
object Dusty {
    /**
     * [Gson] instance.
     */
    val gson: Gson by lazy {
        GsonBuilder()
            .disableHtmlEscaping()
            .create()
    }
}