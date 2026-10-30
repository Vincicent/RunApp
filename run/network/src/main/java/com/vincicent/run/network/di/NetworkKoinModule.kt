package com.vincicent.run.network.di

import com.vincicent.core.domain.run.RemoteRunDataSource
import com.vincicent.run.network.KtorRemoteRunDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::KtorRemoteRunDataSource).bind<RemoteRunDataSource>()
}