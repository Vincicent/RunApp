package com.vincicent.core.data.di

import com.vincicent.core.data.auth.EncryptedSessionStorage
import com.vincicent.core.data.networking.HttpClientFactory
import com.vincicent.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}