@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.vincicent.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.vincicent.auth.domain.repository.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class LoginViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when(action) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnTogglePasswordVisibility -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            else -> Unit
        }
    }

    private fun login() {

    }
}