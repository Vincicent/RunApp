package com.vincicent.runapp

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat
import com.vincicent.auth.data.di.authDataModule
import com.vincicent.auth.presentation.di.authViewModelModule
import com.vincicent.core.data.di.coreDataModule
import com.vincicent.core.database.di.databaseModule
import com.vincicent.run.data.di.runDataModule
import com.vincicent.run.location.di.locationModule
import com.vincicent.run.network.di.networkModule
import com.vincicent.run.presentation.di.runPresentationModule
import com.vincicent.runapp.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
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
            workManagerFactory()
            modules(
                appModule,
                authDataModule,
                authViewModelModule,
                coreDataModule,
                runPresentationModule,
                locationModule,
                databaseModule,
                networkModule,
                runDataModule
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}