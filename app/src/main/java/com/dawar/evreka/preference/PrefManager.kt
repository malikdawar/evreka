package com.dawar.evreka.preference

import android.content.Context
import android.content.SharedPreferences
import com.dawar.evreka.App
import com.dawar.evreka.utils.Const.USER_SESSION


class PrefManager(private val context: Context) {

    companion object {
        private const val SESSION_DETAILS = "SessionDetails"
        private var instance: PrefManager? = null
        fun getInstance(): PrefManager {
            if (instance == null)
                instance =
                    PrefManager(App.getAppContext())
            return instance!!
        }
    }

    var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SESSION_DETAILS, Context.MODE_PRIVATE)

    fun putStringPref(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getStringPref(key: String, defaultKey: String?): String? {
        return sharedPreferences.getString(key, defaultKey)
    }

    fun deleteSpec(key: String) {
        val ed = sharedPreferences.edit()
        ed.remove(key)
        ed.apply()
    }

    fun logoutSession() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun saveSessionDetails(username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_SESSION, username + password)
        editor.apply()
    }

    fun isUserLoggedOut(): Boolean {
        return sharedPreferences.getString(USER_SESSION, "")!!.isEmpty()
    }

    fun saveDouble(key: String, value: Double){
        val editor = sharedPreferences.edit()
        editor.putString(key, value.toString())
        editor.apply()
    }

    fun getDouble(key: String, defaultKey: Double): Double ?{
        return sharedPreferences.getString(key, defaultKey.toString())?.toDouble()
    }
}
