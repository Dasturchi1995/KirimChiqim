package com.example.kirim_chiqim.presintation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.kirim_chiqim.modul.Money
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object MoneyNavType {
    val MoneyNavType = object : NavType<Money>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): Money? {
            return Json.decodeFromString(bundle.getString(key)?: return null)
        }

        override fun parseValue(value: String): Money {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: Money): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Money) {
            bundle.putString(key,Json.encodeToString(value))
        }
    }
}