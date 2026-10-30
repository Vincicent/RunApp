package com.vincicent.run.data

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.vincicent.core.database.dao.RunPendingSyncDao
import com.vincicent.core.database.entity.DeletedRunSyncEntity
import com.vincicent.core.database.entity.RunPendingSyncEntity
import com.vincicent.core.database.mappers.toRunEntity
import com.vincicent.core.domain.SessionStorage
import com.vincicent.core.domain.run.Run
import com.vincicent.core.domain.run.RunId
import com.vincicent.core.domain.run.SyncRunScheduler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.toJavaDuration

class SyncRunWorkerScheduler(
    private val context: Context,
    private val pendingSyncDao: RunPendingSyncDao,
    private val sessionStorage: SessionStorage,
    private val applicationScope: CoroutineScope
): SyncRunScheduler {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun scheduleSync(type: SyncRunScheduler.SyncType) {
        when (type) {
            is SyncRunScheduler.SyncType.CreateRun -> scheduleCreateRunWorker(type.run, type.mapPictureBytes)
            is SyncRunScheduler.SyncType.DeleteRun -> scheduleDeleteRunWorker(type.runId)
            is SyncRunScheduler.SyncType.FetchRuns -> scheduleFetchRunsWorker(type.interval)
        }
    }

    private suspend fun scheduleDeleteRunWorker(runId: RunId) {
        val userId = sessionStorage.get()?.userId ?: return
        val deletedRunEntity = DeletedRunSyncEntity(
            runId = runId,
            userId = userId
        )
        pendingSyncDao.upsertDeletedRunSyncEntity(deletedRunEntity)

        val workRequest = OneTimeWorkRequestBuilder<DeleteRunWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = TimeUnit.MILLISECONDS
            )
            .addTag(DELETE_WORK_TAG)
            .setInputData(
                Data.Builder()
                    .putString(DeleteRunWorker.RUN_ID, deletedRunEntity.runId)
                    .build()
            )
            .build()

        applicationScope.launch {
            workManager.enqueue(workRequest).await()
        }.join()
    }

    private suspend fun scheduleCreateRunWorker(run: Run, mapPictureBytes: ByteArray) {
        val userId = sessionStorage.get()?.userId ?: return
        val pendingRun = RunPendingSyncEntity(
            run = run.toRunEntity(),
            mapPictureBytes = mapPictureBytes,
            userId = userId
        )
        pendingSyncDao.upsertRunPendingSyncEntity(pendingRun)

        val workRequest = OneTimeWorkRequestBuilder<CreateRunWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = TimeUnit.MILLISECONDS
            )
            .addTag(CREATE_WORK_TAG)
            .setInputData(
                Data.Builder()
                    .putString(CreateRunWorker.RUN_ID, pendingRun.runId)
                    .build()
            )
            .build()

        applicationScope.launch {
            workManager.enqueue(workRequest).await()
        }.join()
    }

    private suspend fun scheduleFetchRunsWorker(interval: Duration) {
        val isSyncSchedule = withContext(Dispatchers.IO) {
            workManager.getWorkInfosByTag(SYNC_WORK_TAG)
                .get()
                .isNotEmpty()
        }

        if (isSyncSchedule) return

        val workRequest = PeriodicWorkRequestBuilder<FetchRunsWorker>(
            repeatInterval = interval.toJavaDuration()
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = TimeUnit.MILLISECONDS
            )
            .setInitialDelay(
                duration = 30,
                timeUnit = TimeUnit.MINUTES
            )
            .addTag(SYNC_WORK_TAG)
            .build()

        workManager.enqueue(workRequest).await()
    }

    override suspend fun cancelAllSyncs() {
        WorkManager.getInstance(context)
            .cancelAllWork()
            .await()
    }

    companion object {
        private const val SYNC_WORK_TAG = "sync_work"
        private const val CREATE_WORK_TAG = "create_work"
        private const val DELETE_WORK_TAG = "delete_work"
    }
}