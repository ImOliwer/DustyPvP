package club.rarlab.dustypvp.data

import club.rarlab.dustypvp.config.configurations.BaseOption.*
import club.rarlab.dustypvp.util.logColored
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

/**
 * Object container for database connections.
 */
internal object DatabaseConnection {
    /**
     * [Properties] instance.
     */
    private val properties: Properties = Properties()

    /**
     * Reload properties.
     */
    fun reload() {
        properties.apply {
            setProperty("driver", when (SOURCE_TYPE.toString().toUpperCase()) {
                else -> "com.mysql.jdbc.Driver"
            })
            setProperty("url", when (SOURCE_TYPE.toString().toUpperCase()) {
                else -> "jdbc:mysql://${DB_ADDRESS}:${DB_PORT.toInt()}/${DB_DATABASE}"
            })
            setProperty("username", DB_USERNAME.toString())
            setProperty("password", DB_PASSWORD.toString())
        }
    }

    /**
     * Open up a new connection.
     */
    fun findConnection(): Connection? = try {
        Class.forName(this.getDriver())
        DriverManager.getConnection(this.getUrl(), this.getUsername(), this.getPassword())
                .apply { this.autoCommit = false }
    } catch (ex: Exception) {
        logColored("&cConnection to database has failed!")
        null
    }

    /**
     * Get the url.
     */
    private fun getUrl(): String = properties.getProperty("url")

    /**
     * Get the username.
     */
    private fun getUsername(): String = properties.getProperty("username")

    /**
     * Get the password.
     */
    private fun getPassword(): String = properties.getProperty("password")

    /**
     * Get the driver.
     */
    private fun getDriver(): String = properties.getProperty("driver")
}

/**
 * Enumeration of all database tables.
 */
enum class DatabaseTable(private var value: String) {
    USERS("users"),
    CHALLENGES("challenges");

    /**
     * Get the value.
     */
    override fun toString(): String = this.value

    /**
     * Modify the value.
     */
    fun modify(value: String) {
        this.value = value
    }
}

/**
 * Object of all database queries.
 */
internal object DatabaseQueries {
    // fetch all from a table
    const val FETCH = "SELECT * FROM `{table}`"

    // create the users table
    const val TABLE_USERS = "CREATE TABLE IF NOT EXISTS `{table}` " +
            "(`uuid` VARCHAR(36) PRIMARY KEY, `kills` INT, `deaths` INT);"

    // create the challenges table
    const val TABLE_CHALLENGES = "CREATE TABLE IF NOT EXISTS `{table}` " +
            "(`uuid` VARCHAR(36), `id` INT PRIMARY KEY);"

    // insert / update users table
    const val UPDATE_USERS = "INSERT INTO `{table}` (`uuid`, `kills`, `deaths`) VALUES ({uuid}, {kills}, {deaths}) " +
            "ON DUPLICATE KEY UPDATE `kills`={kills}, `deaths`={deaths}"

    // insert / update challenges table
    const val UPDATE_CHALLENGES = "INSERT INTO `{table}` (`uuid`, `id`) VALUES ({uuid}, {id}) " +
            "ON DUPLICATE KEY UPDATE `uuid`={id}"
}