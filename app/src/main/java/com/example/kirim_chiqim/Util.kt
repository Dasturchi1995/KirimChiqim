package com.example.kirim_chiqim

import java.text.NumberFormat

object Util {

    fun Long?.toMoney(): String{
        if (this == null){
            return ""
        }
        val numberFormat = NumberFormat.getNumberInstance()
        return numberFormat.format(this)
    }
}