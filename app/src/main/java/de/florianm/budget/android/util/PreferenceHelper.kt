package de.florianm.budget.android.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

val Context.defaultPrefs: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(this)

inline fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    action(editor)
    editor.apply()
}

operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String -> edit { putString(key, value) }
        is Int -> edit { putInt(key, value) }
        is Long -> edit { putLong(key, value) }
        is Float -> edit { putFloat(key, value) }
        is Boolean -> edit { putBoolean(key, value) }
        else -> throw UnsupportedOperationException("Currently not supported")
    }
}

operator inline fun <reified T> SharedPreferences.get(key: String, defValue: T? = null): T? {
    when (T::class) {
        String::class -> getString(key, defValue as? String ?: "")
        Int::class -> getInt(key, defValue as? Int ?: 0)
        Long::class -> getLong(key, defValue as? Long ?: 0)
        Float::class -> getFloat(key, defValue as? Float ?: 0.0f)
        Boolean::class -> getBoolean(key, defValue as? Boolean ?: false)
        else -> throw UnsupportedOperationException("${T::class} is currently not supported")
    }

    return defValue
}

@Suppress("IMPLICIT_CAST_TO_ANY")
inline fun <reified T> Context.bindPref(key: String, defValue: T? = null) = lazy {
    val prefs = defaultPrefs
    when (T::class) {
        String::class -> prefs.getString(key, defValue as? String ?: "")
        Int::class -> prefs.getInt(key, defValue as? Int ?: 0)
        Long::class -> prefs.getLong(key, defValue as? Long ?: 0)
        Float::class -> prefs.getFloat(key, defValue as? Float ?: 0.0f)
        Boolean::class -> prefs.getBoolean(key, defValue as? Boolean ?: false)
        else -> throw UnsupportedOperationException("${T::class} is currently not supported")
    }
}

/*
fun Context.bindPrefString(key: String, defValue: String = "") = lazy { defaultPrefs.getString(key, defValue) }
fun Context.bindPrefInt(key: String, defValue: Int = 0) = lazy { defaultPrefs.get(key, defValue) }
fun Context.bindPrefLong(key: String, defValue: Long = 0L) = lazy { defaultPrefs.getLong(key, defValue) }
fun Context.bindPrefFloat(key: String, defValue: Float = 0f) = lazy { defaultPrefs.getFloat(key, defValue) }
fun Context.bindPrefBoolean(key: String, defValue: Boolean = false) = lazy { defaultPrefs.getBoolean(key, defValue) }
fun Context.bindPrefStringSet(key: String, defValue: Set<String> = setOf()) = lazy { defaultPrefs.getStringSet(key, defValue) }
*/
