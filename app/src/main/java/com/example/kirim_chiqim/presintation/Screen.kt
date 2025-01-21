package com.example.kirim_chiqim.presintation

import com.example.kirim_chiqim.modul.Money
import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object MoneyHome

    @Serializable
    data class AddMoney(val money: Money)
}