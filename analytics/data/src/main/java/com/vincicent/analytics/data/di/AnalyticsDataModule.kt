package com.vincicent.analytics.data.di

import com.vincicent.analytics.data.RoomAnalyticsRepository
import com.vincicent.analytics.domain.AnalyticsRepository
import com.vincicent.core.database.RunDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsModule = module {
    singleOf(::RoomAnalyticsRepository).bind<AnalyticsRepository>()
    single {
        get<RunDatabase>().analyticsDao
    }
}