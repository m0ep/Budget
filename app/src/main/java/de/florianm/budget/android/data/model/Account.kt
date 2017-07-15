package de.florianm.budget.android.data.model

/**
 * Account that holds money
 */
data class Account(
        val id: String,
        val budgetId: String,
        val name: String,
        val type: AccountType,
        val sortIndex: Int = 0,
        val hidden: Boolean = false,
        val onBudget: Boolean = true,
        val note: String = ""
)

