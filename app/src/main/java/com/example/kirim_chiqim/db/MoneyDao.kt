package com.example.kirim_chiqim.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.kirim_chiqim.modul.Money
import com.example.kirim_chiqim.modul.MoneyType
import com.example.kirim_chiqim.modul.Sum
import com.example.kirim_chiqim.navigation.MoneyEvent
import kotlinx.coroutines.flow.Flow


@Dao
interface MoneyDao {

    @Insert
    suspend fun insertMoney(money: Money): Long

    @Update
    suspend fun updateMoney(money: Money)

    @Delete
   suspend fun getMoneyDelete(money: Money): Int

    @Query("select sum(summa) from money")
    fun getAllSumma():Flow<Double>

    @Query("select * from money where summa like :query or title like :query order by created_at")
    fun getMoney(query: String): Flow<List<Money>>

    @RawQuery(observedEntities = [Money::class])
    fun getMoneyListFilter(query: SupportSQLiteQuery): Flow<List<Money>>

    @Query("select sum(summa) as 'totalSum'," +
            " sum(case when type='INCOME' then summa else 0 end) as 'income',"+
            " sum(case when type='EXPANSE' then summa else 0 end) as 'expanse'" +
            " from money")
    fun getAllSum(): Flow<Sum>

    @Query("select sum(summa) as 'totalSum'," +
            " sum(case when type='INCOME' then summa else 0 end) as 'income',"+
            " sum(case when type='EXPANSE' then summa else 0 end) as 'expanse'" +
            " from money where created_at>:startDate and created_at<:endDate")
    fun getSum(startDate: Long, endDate: Long): Flow<Sum>

}