package com.vincicent.auth.domain.repository

import com.vincicent.core.domain.util.DataError
import com.vincicent.core.domain.util.EmptyResult

interface AuthRepository {

    suspend fun login(email: String, password: String): EmptyResult<DataError.Network>
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}