package com.vincicent.auth.data.repository

import com.vincicent.auth.data.LoginRequest
import com.vincicent.auth.data.LoginResponse
import com.vincicent.auth.data.RegisterRequest
import com.vincicent.auth.domain.repository.AuthRepository
import com.vincicent.core.data.networking.post
import com.vincicent.core.domain.AuthInfo
import com.vincicent.core.domain.SessionStorage
import com.vincicent.core.domain.util.DataError
import com.vincicent.core.domain.util.EmptyResult
import com.vincicent.core.domain.util.Result
import com.vincicent.core.domain.util.asEmptyResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
): AuthRepository {
    override suspend fun login(email: String, password: String): EmptyResult<DataError.Network> {
        val result =  httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )
        if (result is Result.Success) {
            sessionStorage.set(
                AuthInfo(
                    accessToken = result.data.accessToken,
                    refreshToken = result.data.refreshToken,
                    userId = result.data.userId
                )
            )
        }
        return result.asEmptyResult()
    }

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }
}