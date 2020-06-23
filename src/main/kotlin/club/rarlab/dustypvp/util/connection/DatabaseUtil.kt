package club.rarlab.dustypvp.util.connection

import club.rarlab.dustypvp.config.configurations.BaseOption
import club.rarlab.dustypvp.data.DataSource
import club.rarlab.dustypvp.data.DatabaseConnection
import club.rarlab.dustypvp.data.DatabaseTable
import club.rarlab.dustypvp.data.sources.FlatSource
import club.rarlab.dustypvp.data.sources.MySQLSource
import club.rarlab.dustypvp.util.bukkit.SchedulerUtil.callAsync
import org.bukkit.plugin.Plugin
import java.sql.Statement

/**
 * Send single statement query.
 */
fun queryStatement(
        commit: Boolean,
        statementAction: (Statement) -> Unit
) = callAsync { DatabaseConnection.findConnection()?.use { connection ->
    connection.createStatement().use { statement ->
        statementAction(statement)
        if (commit) connection.commit()
    }
}}

/**
 * Replace the corresponding [DatabaseTable] in a [String].
 */
fun String.replaceTable(table: DatabaseTable): String = replace("{table}", table.toString())

/**
 * Fetch the corresponding data source.
 */
fun fetchCorrespondingSource(plugin: Plugin): DataSource<*> =
        when (BaseOption.SOURCE_TYPE.toString().toUpperCase()) {
            "MYSQL" -> MySQLSource(plugin)
            else -> FlatSource(plugin)
        }