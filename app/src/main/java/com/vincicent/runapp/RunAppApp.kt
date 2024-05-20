package com.vincicent.runapp

import android.app.Application
import com.vincicent.auth.data.di.authDataModule
import com.vincicent.auth.presentation.di.authViewModelModule
import com.vincicent.core.data.di.coreDataModule
import com.vincicent.run.presentation.di.runPresentationModule
import com.vincicent.runapp.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RunAppApp: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RunAppApp)
            modules(
                appModule,
                authDataModule,
                authViewModelModule,
                coreDataModule,
                runPresentationModule
            )
        }
    }
}