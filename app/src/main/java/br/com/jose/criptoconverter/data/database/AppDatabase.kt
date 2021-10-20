package br.com.jose.criptoconverter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.jose.criptoconverter.data.database.dao.ExchangeDao
import br.com.jose.criptoconverter.data.model.ExchangeResponseValue

@Database(entities = [ExchangeResponseValue::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun exchangeDao(): ExchangeDao

    companion object{
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "exchange_db"
            ).fallbackToDestructiveMigration().build()
        }
    }
}