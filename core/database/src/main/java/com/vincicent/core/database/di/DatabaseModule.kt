package com.vincicent.core.database.di

import androidx.room.Room
import com.vincicent.core.database.RoomLocalRunDataSource
import com.vincicent.core.database.RunDatabase
import com.vincicent.core.domain.run.LocalRunDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            RunDatabase::class.java,
            "run.db"
        ).build()
    }
    single { get<RunDatabase>().runDao }

    singleOf(::RoomLocalRunDataSource).bind<LocalRunDataSource>()
}