package com.vincicent.auth.data.di

import com.vincicent.auth.data.EmailPatternValidator
import com.vincicent.auth.domain.PatternValidator
import com.vincicent.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
}