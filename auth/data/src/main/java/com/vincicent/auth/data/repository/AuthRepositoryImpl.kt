package com.vincicent.auth.data.repository

import com.vincicent.auth.data.RegisterRequest
import com.vincicent.auth.domain.repository.AuthRepository
import com.vincicent.core.data.networking.post
import com.vincicent.core.domain.util.DataError
import com.vincicent.core.domain.util.EmptyResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient
): AuthRepository {
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