package de.florianm.budget.android.data

import android.database.sqlite.SQLiteDatabase

abstract class TableConfig {
    abstract fun createTable(db : SQLiteDatabase)
    abstract fun upgradeTable(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
}