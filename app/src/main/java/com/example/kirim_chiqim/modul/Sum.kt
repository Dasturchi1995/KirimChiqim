package com.example.kirim_chiqim.modul

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sum(
    @PrimaryKey
    val totalSum: Long = 0,
    val income: Long = 0,
    val expanse: Long = 0,
)
