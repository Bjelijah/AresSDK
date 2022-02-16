package com.cbj.sdk.libbase.utils

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.blankj.utilcode.util.Utils
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author:cbj
 * @Date: 2022/2/16
 * @Description:
 */
open class SharedPreferencesUtil {



    var token by SharedPreferenceDelegates.string("")


    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(Utils.getApp())


    private object SharedPreferenceDelegates {
        fun int(defaultValue: Int = 0) = object : ReadWriteProperty<SharedPreferencesUtil, Int> {
            override fun getValue(thisRef: SharedPreferencesUtil, property: KProperty<*>): Int {
                return thisRef.preferences.getInt(property.name, defaultValue)
            }
            override fun setValue(
                thisRef: SharedPreferencesUtil,
                property: KProperty<*>,
                value: Int
            ) {
                thisRef.preferences.edit().putInt(property.name, value).apply()
            }
        }
        fun long(defaultValue: Long = 0L) =
            object : ReadWriteProperty<SharedPreferencesUtil, Long> {

                override fun getValue(
                    thisRef: SharedPreferencesUtil,
                    property: KProperty<*>
                ): Long {
                    return thisRef.preferences.getLong(property.name, defaultValue)
                }

                override fun setValue(
                    thisRef: SharedPreferencesUtil,
                    property: KProperty<*>,
                    value: Long
                ) {
                    thisRef.preferences.edit().putLong(property.name, value).apply()
                }
            }

        fun boolean(defaultValue: Boolean = false) =
            object : ReadWriteProperty<SharedPreferencesUtil, Boolean> {
                override fun getValue(
                    thisRef: SharedPreferencesUtil,
                    property: KProperty<*>
                ): Boolean {
                    return thisRef.preferences.getBoolean(property.name, defaultValue)
                }

                override fun setValue(
                    thisRef: SharedPreferencesUtil,
                    property: KProperty<*>,
                    value: Boolean
                ) {
                    thisRef.preferences.edit().putBoolean(property.name, value).apply()
                }
            }

        fun float(defaultValue: Float = 0.0f) =
            object : ReadWriteProperty<SharedPreferencesUtil, Float> {
                override fun getValue(
                    thisRef: SharedPreferencesUtil,
                    property: KProperty<*>
                ): Float {
                    return thisRef.preferences.getFloat(property.name, defaultValue)
                }

                override fun setValue(
                    thisRef: SharedPreferencesUtil,
                    property: KProperty<*>,
                    value: Float
                ) {
                    thisRef.preferences.edit().putFloat(property.name, value).apply()
                }
            }

        fun string(defaultValue: String? = null) =
            object : ReadWriteProperty<SharedPreferencesUtil, String?> {
                override fun getValue(
                    thisRef: SharedPreferencesUtil,
                    property: KProperty<*>
                ): String? {
                    return thisRef.preferences.getString(property.name, defaultValue)
                }

                override fun setValue(
                    thisRef: SharedPreferencesUtil,
                    property: KProperty<*>,
                    value: String?
                ) {
                    thisRef.preferences.edit().putString(property.name, value).apply()
                }
            }


    }
}