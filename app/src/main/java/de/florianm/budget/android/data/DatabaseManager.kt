package de.florianm.budget.android.data

import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.squareup.sqlbrite2.BriteDatabase
import com.squareup.sqlbrite2.SqlBrite
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class DatabaseManager(
        val context: android.content.Context,
        val name: String,
        val version: Int,
        val tableConfigs: MutableList<TableConfig>,
        foreignKeyConstraints: Boolean = false,
        factory: SQLiteDatabase.CursorFactory? = null,
        errorHandler: DatabaseErrorHandler? = null,
        logger: SqlBrite.Logger? = null,
        scheduler: Scheduler = Schedulers.io(),
        tablesCreatedListener: (db: SQLiteDatabase) -> Unit = { _ -> },
        tablesUpgradedListener: (db: SQLiteDatabase, oldVersion: Int, newVersion: Int) -> Unit = { _, _, _ -> }) {


    private var database: BriteDatabase

    init {
        val openHelper = OpenHelper(
                context,
                name,
                factory,
                version,
                errorHandler,
                foreignKeyConstraints,
                tablesCreatedListener,
                tablesUpgradedListener)
        val sqlBride = SqlBrite.Builder().logger(logger ?: SqlBrite.Logger { _ -> }).build()
        database = sqlBride.wrapDatabaseHelper(openHelper, scheduler)
        database.setLoggingEnabled(null != logger)
    }

    fun getDatabase(): BriteDatabase {
        return database
    }

    /**
     * Delete the database of this DatabaseManager
     */
    fun deleteSqliteDatabase() {
        context.deleteDatabase(name)
    }

    /**
     * Get the path to the database of this DatabaseManager.
     */
    fun getSqliteDatabasePath(): java.io.File? = context.getDatabasePath(name)

    inner class OpenHelper(
            context: android.content.Context,
            name: String,
            factory: SQLiteDatabase.CursorFactory?,
            version: Int = 1,
            errorHandler: DatabaseErrorHandler?,
            val foreignKeyConstraints: Boolean,
            val tablesCreatedListener: (db: SQLiteDatabase) -> Unit,
            val tablesUpgradedListener: (db: SQLiteDatabase, oldVersion: Int, newVersion: Int) -> Unit)
        : SQLiteOpenHelper(context, name, factory, version, errorHandler) {

        @android.annotation.SuppressLint("ObsoleteSdkInt")
        override fun onOpen(db: SQLiteDatabase?) {
            super.onOpen(db)

            if (android.os.Build.VERSION.SDK_INT < 16) {
                if (foreignKeyConstraints) {
                    db?.execSQL("PRAGMA foreign_keys=ON;")
                }
            }
        }

        override fun onCreate(db: SQLiteDatabase) {
            tableConfigs.forEach {
                it.createTable(db)
            }

            tablesCreatedListener(db)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            tableConfigs.forEach {
                it.upgradeTable(db, oldVersion, newVersion)
            }

            tablesUpgradedListener(db, oldVersion, newVersion)
        }
    }
}