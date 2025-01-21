package com.example.kirim_chiqim.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.kirim_chiqim.Util.toMoney
import com.example.kirim_chiqim.db.MoneyRepository
import com.example.kirim_chiqim.modul.FilterDataType
import com.example.kirim_chiqim.modul.Money
import com.example.kirim_chiqim.modul.MoneyType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class MoneyViewModel @Inject constructor(
    val repository: MoneyRepository
): ViewModel(){

    private val _state = MutableStateFlow(MoneyState())
    val state = _state.asStateFlow()

    fun onEvent(event: MoneyEvent){
        when(event){
            MoneyEvent.GetAllMoney->{
                filterMoneyList()
            }
            is MoneyEvent.SearchMoney->{
                val query = "%${event.query.trim().lowercase()}%"
                filterMoneyList(searchQuery = "%$query%")
            }
            MoneyEvent.SaveMoney->{
                val money = Money(
                    id = state.value.money.id,
                    summa = state.value.summa.toLong(),
                    title = state.value.text,
                    type = state.value.type
                )
                if (money.id == null){
                    insertMoney(money)
                }
                else{
                    updateMoney(money)
                }
            }
           is MoneyEvent.UpdateSumma -> {
                _state.update {
                    it.copy(
                       summa = event.summa
                    )
                }
            }
            is MoneyEvent.UpdateText -> {
                _state.update {
                    it.copy(
                        text = event.text
                    )
                }
            }

            is MoneyEvent.UpdateRadioButton->{
                _state.update {
                    it.copy(
                        type = event.type
                    )
                }
            }
            MoneyEvent.ClearMoney -> {
                _state.update {
                    it.copy(
                        summa = "",
                        text = "",
                        money = Money()
                    )
                }
                println("Clear= ${MoneyEvent.ClearMoney}")
            }

            is MoneyEvent.DeleteMoney -> {
                getMoneyDelete(event.money)
            }

            is MoneyEvent.SetMoney -> {
                _state.update {
                    it.copy(
                        money = event.money,
                        summa = (event.money.summa).toString(),
                        text = event.money.title
                    )
                }
            }

            is MoneyEvent.AllSumma -> {
                getAllSumma()
            }

            MoneyEvent.Filter -> {
                filterMoneyList()
            }
            is MoneyEvent.UpdateFilterByDate -> {
                _state.update {
                    it.copy(
                        filterDataType = event.filterByDate
                    )
                }
            }
            is MoneyEvent.UpdateFilterMoneyType -> {
                _state.update {
                    it.copy(
                        filterMoneyType = event.moneyType
                    )
                }
            }
            is MoneyEvent.UpdateFilterStartDate -> {
                val startDate = event.date
                val startDateTime = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                _state.update {
                    it.copy(
                        startDate = startDateTime
                    )
                }
            }
            is MoneyEvent.UpdateFilterEndDate -> {
                val endDate = event.date
                val endDateTime = endDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                _state.update {
                    it.copy(
                        endDate = endDateTime
                    )
                }
            }
        }
    }

    private fun getAllSumma() {
        viewModelScope.launch {
            repository.getAllSumma().collectLatest { summa->
                _state.update {
                    it.copy()
                }
            }
        }
    }


    private fun insertMoney(money: Money){
        viewModelScope.launch {
            val result = repository.insertMoney(money)
        }.invokeOnCompletion { excaption->
            if (excaption == null){
                _state.update {
                    it.copy()
                }
            }
        }
    }
    private fun updateMoney(money: Money){
        viewModelScope.launch {
            val result = repository.updateMoney(money)
        }.invokeOnCompletion { excaption->
            if (excaption == null){
                _state.update {
                    it.copy(
                        updateSuccess = true
                    )
                }
            }
        }
    }
    private fun getAllMoney(query: String = "%%"){
        viewModelScope.launch {
            repository.getMoney(query).collectLatest { money->
                _state.update {
                    it.copy(
                        moneyList = money
                    )
                }
            }
        }
    }
    private fun getMoneyDelete(money: Money){
        viewModelScope.launch {
            val result = repository.getMoneyDelete(money = money)
            if (result > 0){
                _state.update {
                    it.copy(
                        deleteSuccess = true
                    )
                }
            }
        }
    }
    private fun filterMoneyList(searchQuery: String = "%%"){
        var query = "select * from money where title like '$searchQuery' order by created_at desc"

        when(state.value.filterDataType){
            FilterDataType.ALL->{
                requestMoneyReportQuery()
                when(state.value.filterMoneyType){
                    MoneyType.ALL->{}
                    MoneyType.INCOME->{
                        query = "select * from money" +
                                " where type='${MoneyType.INCOME.name}' and title like '$searchQuery'" +
                                " order by created_at desc"
                    }
                    MoneyType.EXPANSE->{
                        query = "select * from money" +
                                " where type='${MoneyType.EXPANSE.name}' and title like '$searchQuery'" +
                                " order by created_at desc"
                    }
                }
            }
            FilterDataType.TODAY->{
                val startDate = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
                val endDate = LocalDate.now().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                requestMoneyReportQuery(startDate,endDate)

                when(state.value.filterMoneyType){
                    MoneyType.ALL->{
                        query = "select * from money" +
                                " where created_at>$startDate and created_at<$endDate and title like '$searchQuery'" +
                                " order by created_at desc"
                    }
                    MoneyType.INCOME->{
                        query = "select * from money" +
                                " where type='${MoneyType.INCOME.name}' and created_at>$startDate and created_at<$endDate" +
                                " and title like '$searchQuery'" +
                                " order by created_at desc"
                    }
                    MoneyType.EXPANSE->{
                        query = "select * from money" +
                                " where type='${MoneyType.EXPANSE.name}' and created_at>$startDate and created_at<$endDate" +
                                " and title like '$searchQuery'" +
                                " order by created_at desc"
                    }
                }
            }
            FilterDataType.ANY->{
                val startDate = state.value.startDate
                val endDate = state.value.endDate
                requestMoneyReportQuery(startDate,endDate)

                when(state.value.filterMoneyType){
                    MoneyType.ALL->{
                        query = "select * from money" +
                                " where created_at>$startDate and created_at<$endDate and title like '$searchQuery'" +
                                " order by created_at desc"
                    }
                    MoneyType.INCOME->{
                        query = "select * from money" +
                                " where type='${MoneyType.INCOME.name}' and created_at>$startDate and created_at<$endDate" +
                                " and title like '$searchQuery'" +
                                " order by created_at desc"
                    }
                    MoneyType.EXPANSE->{
                        query = "select * from money" +
                                " where type='${MoneyType.EXPANSE.name}' and created_at>$startDate and created_at<$endDate" +
                                " and title like '$searchQuery'" +
                                " order by created_at desc"
                    }
                }
            }
        }
        requestSqlQuery(query)
    }
    private fun requestSqlQuery(query: String){
        viewModelScope.launch {
            repository.getMoneyListFilter(SimpleSQLiteQuery(query)).collectLatest { moneyList->
                _state.update {
                    it.copy(
                        moneyList = moneyList
                    )
                }
            }
        }
    }

    private fun requestMoneyReportQuery(startDate: Long? = null, endDate: Long? = null){
        viewModelScope.launch {
            repository.getSum(startDate,endDate).collectLatest { sum->
                _state.update {
                    it.copy(
                        allSum = sum.totalSum.toMoney(),
                        incomeSum = sum.income.toMoney(),
                        expanseSum = sum.expanse.toMoney()
                    )
                }
            }
        }
    }
}