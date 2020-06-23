package club.rarlab.dustypvp.data

import club.rarlab.dustypvp.helper.Callback
import club.rarlab.dustypvp.structure.DustyPlayer

/**
 * Interface for data sources.
 */
interface DataSource<T> {
    /**
     * Load all saved data.
     *
     * @param callBack [Callback] to be called when loading is done.
     */
    fun loadData(callBack: Callback<Map<String, DustyPlayer>>): T

    /**
     * Save all cached data.
     */
    fun saveData(notify: Boolean)

    /**
     * Prepare the source.
     */
    fun prepare() { throw UnsupportedOperationException("Preparation is not supported.") }

    /**
     * Get the [DataSource] type.
     */
    fun getType(): DataType
}

/**
 * All available types of data sources.
 */
enum class DataType(private val _name: String, val isDatabase: Boolean) {
    MYSQL("MySQL", isDatabase = true),
    FLAT("Flat", isDatabase = false);

    /**
     * Override base method to return the data type's name.
     */
    override fun toString(): String = this._name
}