package de.florianm.budget.android.app

import android.app.Application
import de.florianm.budget.android.data.DatabaseManager
import de.florianm.budget.android.data.table.BudgetTable
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        val dbManager = DatabaseManager(
                this,
                "default.db",
                1, mutableListOf(BudgetTable())
        )
    }
}