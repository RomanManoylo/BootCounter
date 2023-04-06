package com.example.bootscounter.di

import androidx.room.Room
import com.example.bootscounter.database.AppDatabase
import com.example.bootscounter.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Modules {
    val main = module {
        single {
            Room.databaseBuilder(androidContext(), AppDatabase::class.java, "boot")
                .fallbackToDestructiveMigration()
                .build()
        }
        viewModel{
            MainViewModel(get())
        }
    }
}