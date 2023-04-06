package com.example.bootscounter.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bootscounter.database.model.Boot

@Dao
interface BootDao {

    @Query("SELECT * FROM Boot")
    fun getAll(): List<Boot>

    @Insert
    fun insert(boot: Boot)
}
