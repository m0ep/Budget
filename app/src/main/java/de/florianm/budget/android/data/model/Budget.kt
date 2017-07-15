package de.florianm.budget.android.data.model

import java.util.*


data class Budget(
        val id: String,
        val name: String,
        val currency: Currency,
        val numberFormat: String,
        val dateFormat: String
)