package com.example.kirim_chiqim.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kirim_chiqim.modul.Money


@Database(entities = [Money::class], version = 1)
abstract class MoneyDatabase: RoomDatabase() {
    abstract val dao: MoneyDao

    companion object{
        const val DB_NAME = "Money.db"
    }
}