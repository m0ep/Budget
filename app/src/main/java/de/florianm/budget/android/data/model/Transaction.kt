package de.florianm.budget.android.data.model

import org.threeten.bp.LocalDate
import java.math.BigDecimal


/**
 * Transactions between accounts
 */
data class Transaction (
        val id:String,
        val accountId: String,
        val payeeId: String,
        val categoryId: String,
        val amount: BigDecimal,
        val date: LocalDate,
        val note: String
)