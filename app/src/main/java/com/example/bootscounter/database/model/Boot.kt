package com.example.bootscounter.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boot")
data class Boot(
    @PrimaryKey
    @ColumnInfo(name = "timestamp") val timestamp: Long
)
