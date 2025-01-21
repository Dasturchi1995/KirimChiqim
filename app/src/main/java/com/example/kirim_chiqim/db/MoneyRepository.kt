package com.example.kirim_chiqim.db

import androidx.sqlite.db.SupportSQLiteQuery
import com.example.kirim_chiqim.modul.Money
import com.example.kirim_chiqim.modul.Sum
import com.example.kirim_chiqim.navigation.MoneyEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class MoneyRepository @Inject constructor(
    val db: MoneyDatabase
){
    val repository = db.dao

    suspend fun insertMoney(money: Money) = repository.insertMoney(money)

    suspend fun updateMoney(money: Money) = repository.updateMoney(money)

    fun getMoney(query: String) = repository.getMoney(query)

   suspend fun getMoneyDelete(money: Money) = repository.getMoneyDelete(money)

    fun getAllSumma() = repository.getAllSumma()

    fun getMoneyListFilter(query: SupportSQLiteQuery) = repository.getMoneyListFilter(query)

    fun getSum(startDate: Long?, endDate: Long?) = if (startDate == null || endDate == null) {
        repository.getAllSum()
    } else {
        repository.getSum(startDate, endDate)
    }
}


