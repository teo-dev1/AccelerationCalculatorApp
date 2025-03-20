package com.example.punchspeedcalculator.utils

import android.content.Context

class SharedPrefsManager(private val c: Context) {
    private val sharedTag = "SHARED"
    private val valueTag = "USER_UID"

    fun saveUidInSharedPrefs(uId: String) {
        c.getSharedPreferences(sharedTag, 0).edit().putString(valueTag, uId).apply()
    }

    fun getUid(): String? {
        return c.getSharedPreferences(sharedTag, 0).getString(valueTag, "/")
    }

    fun clearUserUid() {
       c.getSharedPreferences(sharedTag,0).edit().remove(valueTag).apply()
    }
}