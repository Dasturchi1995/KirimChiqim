package com.example.kirim_chiqim.modul

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
@Entity(tableName = "money")
data class Money(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val summa: Long = 0,
    val title: String = "",
    val created_at: Long = System.currentTimeMillis(),
    val type: MoneyType? = MoneyType.INCOME
)

@Serializable
enum class MoneyType{
    INCOME,
    EXPANSE,
    ALL
}
