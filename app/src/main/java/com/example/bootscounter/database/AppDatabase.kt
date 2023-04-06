package com.example.bootscounter.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bootscounter.database.dao.BootDao
import com.example.bootscounter.database.model.Boot

@Database(
    version = 1,
    entities = [
        Boot::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bootDao(): BootDao
}