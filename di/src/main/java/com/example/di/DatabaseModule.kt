package com.example.di

import androidx.room.Room
import com.example.data.database.MarvelDB
import org.koin.dsl.module

object DBModule {

    private const val REPOSITORY_NAME = "MarvelRepository"

    val dbModule = module {
        single {
            Room.databaseBuilder(
                get(),
                MarvelDB::class.java,
                REPOSITORY_NAME
            ).build()
        }
        single { get<MarvelDB>().marvelDaO() }
    }
}
