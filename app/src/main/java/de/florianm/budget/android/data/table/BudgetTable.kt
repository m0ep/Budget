package de.florianm.budget.android.data.table

import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import de.florianm.budget.android.data.TableConfig
import timber.log.Timber

class BudgetTable : TableConfig() {

    override fun createTable(db: SQLiteDatabase) {
        val sql = """
        CREATE TABLE IF NOT EXIST $TABLE_NAME (
            $COL_ID TEXT NOT NULL PRIMARY,
            $COL_NAME TEXT NOT NULL,
            $COL_CURRENCY TEXT,
            $COL_NUMBER_FORMAT TEXT,
            $COL_DATE_FORMAT TEXT,
            $COL_VERSION INTEGER NOT NULL DEFAULT(0)
        )"""

        try {
            db.execSQL(sql)
        } catch (e: SQLException) {
            Timber.e(e, "Failed to execute \n%s", sql)
        }
    }

    override fun upgradeTable(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        val TABLE_NAME = "budget"

        val COL_ID = "_id"
        val COL_NAME = "name"
        val COL_CURRENCY = "currency"
        val COL_NUMBER_FORMAT = "number_format"
        val COL_DATE_FORMAT = "date_format"
        val COL_VERSION = "version"
    }

}