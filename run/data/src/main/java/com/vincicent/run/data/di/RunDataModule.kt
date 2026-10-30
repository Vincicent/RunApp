package com.vincicent.run.data.di

import com.vincicent.core.domain.run.SyncRunScheduler
import com.vincicent.run.data.CreateRunWorker
import com.vincicent.run.data.DeleteRunWorker
import com.vincicent.run.data.FetchRunsWorker
import com.vincicent.run.data.SyncRunWorkerScheduler
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)
    workerOf(::CreateRunWorker)

    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
}