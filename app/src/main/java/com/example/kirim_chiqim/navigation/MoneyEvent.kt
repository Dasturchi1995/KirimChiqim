package com.example.kirim_chiqim.navigation

import com.example.kirim_chiqim.modul.FilterDataType
import com.example.kirim_chiqim.modul.Money
import com.example.kirim_chiqim.modul.MoneyType
import java.time.LocalDate

sealed interface MoneyEvent {
    object GetAllMoney: MoneyEvent
    object ClearMoney: MoneyEvent
    object SaveMoney: MoneyEvent
    data class AllSumma(val summa: String = ""): MoneyEvent
    data class SetMoney(val money: Money): MoneyEvent
    data class UpdateRadioButton(val type:MoneyType = MoneyType.INCOME):MoneyEvent
    data class UpdateSumma(val summa: String = ""): MoneyEvent
    data class UpdateText(val text: String = ""): MoneyEvent
    data class SearchMoney(val query: String = ""): MoneyEvent
    data class DeleteMoney(val money: Money): MoneyEvent
    data class UpdateFilterMoneyType(val moneyType: MoneyType): MoneyEvent
    data class UpdateFilterByDate(val filterByDate: FilterDataType): MoneyEvent
    data class UpdateFilterStartDate(val date: LocalDate): MoneyEvent
    data class UpdateFilterEndDate(val date: LocalDate): MoneyEvent
    object Filter: MoneyEvent
}