package com.farida.kotlin_api_weather.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CityEntity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {

        @Volatile private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): WeatherDatabase {
            val name = "weather.db"
            return Room.databaseBuilder(context.applicationContext,
                WeatherDatabase::class.java, name)
                .build()
        }
    }
}