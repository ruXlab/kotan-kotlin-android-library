package vc.rux.kotan

import android.content.Context
import android.content.SharedPreferences

/**
 * Shared preferences helper
 * (obsolete way)
 */

fun <T> Context.loadPref(key: String, default: T): T {
    val sp = getSharedPreferences(javaClass.getCanonicalName(), 0)
    return when (default) {
        is Int -> sp.getInt(key, default)
        is Boolean -> sp.getBoolean(key, default)
        is String -> sp.getString(key, default)
        is Float -> sp.getFloat(key, default)
        is Long -> sp.getLong(key, default)
        else -> default
    } as T
}

fun <T> Context.savePref(key: String, value: T) {
    val sp = getSharedPreferences(javaClass.getCanonicalName(), 0).edit()
    when (value) {
        is Int -> sp.putInt(key, value)
        is Boolean -> sp.putBoolean(key, value)
        is String -> sp.putString(key, value)
        is Float -> sp.putFloat(key, value)
        is Long -> sp.putLong(key, value)
        else -> return
    }
    sp.commit()
}

fun SharedPreferences.edit(action: (editor: SharedPreferences.Editor) -> Unit) {
    val sp = edit()
    action(sp)
    sp.commit()
}


