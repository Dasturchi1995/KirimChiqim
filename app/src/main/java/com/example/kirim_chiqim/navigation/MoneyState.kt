package com.example.kirim_chiqim.navigation

import com.example.kirim_chiqim.modul.FilterDataType
import com.example.kirim_chiqim.modul.Money
import com.example.kirim_chiqim.modul.MoneyType

data class MoneyState(
    val moneyList: List<Money> = emptyList(),
    val summa: String = "",
    val text: String = "",
    val money: Money = Money(),
    val type: MoneyType = MoneyType.INCOME,
    val filterMoneyType: MoneyType = MoneyType.ALL,
    val filterDataType: FilterDataType = FilterDataType.ALL,
    val startDate: Long = 0,
    val endDate: Long = 0,
    val updateSuccess: Boolean = false,
    val deleteSuccess: Boolean = false,

    val allSum: String = "0",
    val incomeSum: String = "0",
    val expanseSum: String = "0",
)
