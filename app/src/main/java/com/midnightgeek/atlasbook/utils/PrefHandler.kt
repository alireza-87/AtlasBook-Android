package com.midnightgeek.atlasbook.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**
 * <p>Pref Handler</p>
 * This class used to handle pref method
 */
class PrefHandler @Inject constructor(application: Application) {

    private val application: Application = application
    private lateinit var sharedPref: SharedPreferences
    private val PREF_NAME = "atlaspref"
    val TAG_IS_LOGIN = "isLogin"
    val TAG_IS_GUEST = "isGuest"
    val FCM_TOKEN = "FCM_TOKEN"
    val FCM_TOKEN_IS_VALID = "FCM_TOKEN_IS_VALID"

    val TAG_USER_NAME = "user_name"

    init {
        sharedPref = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setPreference(key: String?, value: Any?) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        if (value is Int) editor.putInt(
            key,
            (value as Int?)!!
        ) else if (value is String) editor.putString(
            key,
            value as String?
        ) else if (value is Boolean) editor.putBoolean(
            key,
            (value as Boolean?)!!
        ) else if (value is Long) editor.putLong(
            key,
            (value as Long?)!!
        ) else if (value is Set<*>) editor.putStringSet(
            key,
            value as Set<String?>?
        )
        editor.apply()
    }

    fun getInt(key: String?, defaultValue: Int): Int {
        return sharedPref.getInt(key, defaultValue)
    }

    fun getString(key: String?, defaultValue: String?): String? {
        return sharedPref.getString(key, defaultValue)
    }

    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(key, defaultValue)
    }

    fun getLong(key: String?, defaultValue: Long): Long {
        return sharedPref.getLong(key, defaultValue)
    }

    fun getStringSet(
        key: String?,
        defaultValue: Set<String?>?
    ): Set<String?>? {
        return sharedPref.getStringSet(key, defaultValue)
    }

    fun clearTag(keyName: String?) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(keyName)
        editor.apply()
    }

    fun clear(): Boolean {
        return sharedPref.edit().clear().commit()
    }

    fun contain(key: String?): Boolean {
        return sharedPref.contains(key)
    }

    fun RemovingSinglePreference(key: String?) {
        sharedPref.edit().remove(key).apply()
    }

}