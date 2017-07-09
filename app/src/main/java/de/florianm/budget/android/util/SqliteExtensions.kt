package de.florianm.budget.android.util

import android.content.ContentValues
import android.database.Cursor
import com.squareup.sqlbrite2.BriteDatabase
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter

val dateTimeFormater = DateTimeFormatter.ISO_DATE_TIME

fun <T> BriteDatabase.transaction(body: BriteDatabase.() -> T) : T{
    val transaction = newTransaction()
    try{
        val result = body()
        transaction.markSuccessful()
        return result
    } finally {
        transaction.end()
    }
}

fun Cursor.getStringOrThrow(columnName: String): String {
    val index = getColumnIndexOrThrow(columnName)
    if (isNull(index)) {
        throw NullPointerException("The value of column \"$columnName\" was null")
    }
    return getString(index)
}

fun Cursor.getString(columnName: String): String? {
    val index = getColumnIndex(columnName)
    if (0 > index) {
        return null
    }

    return getString(index)
}

fun Cursor.getLongOrThrow(columnName: String): Long {
    val index = getColumnIndexOrThrow(columnName)
    if (isNull(index)) {
        throw NullPointerException("The value of column \"$columnName\" was null")
    }
    return getLong(index)
}

fun Cursor.getLong(columnName: String): Long? {
    val index = getColumnIndex(columnName)
    if (0 > index || isNull(index)) {
        return null
    }

    return getLong(index)
}

fun Cursor.getOffsetDateTimeOrThrow(columnName: String): OffsetDateTime {
    val index = getColumnIndexOrThrow(columnName)
    if (isNull(index)) {
        throw NullPointerException("The value of column \"$columnName\" was null")
    }
    val value = getString(index)
    return OffsetDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME)
}

fun Cursor.getOffsetDateTime(columnName: String): OffsetDateTime? {
    val index = getColumnIndex(columnName)
    if (0 > index || isNull(index)) {
        return null
    }

    val value = getString(index)
    return OffsetDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME)
}

fun ContentValues.put(key: String, value: OffsetDateTime?) {
    val stringValue = value?.format(DateTimeFormatter.ISO_DATE_TIME)
    put(key, stringValue)
}

fun ContentValues.getAsOffsetDateTime(key : String) : OffsetDateTime?{
    val value = getAsString(key)
    return if(null == value) null else OffsetDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME)
}