package com.example.kirim_chiqim.di

import android.app.Application
import androidx.room.Room
import com.example.kirim_chiqim.db.MoneyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MoneyDatabase{
        return Room.databaseBuilder(
            app,
            MoneyDatabase::class.java,
            MoneyDatabase.DB_NAME
        )
            .fallbackToDestructiveMigrationFrom()
            .build()
    }
}