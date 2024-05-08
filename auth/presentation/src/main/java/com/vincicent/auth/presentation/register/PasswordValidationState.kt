package com.vincicent.auth.presentation.register

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasLowerCaseCharacter: Boolean = false,
    val hasUpperCaseCharacter: Boolean = false,
) {
    val isValidPassword: Boolean
        get() = hasNumber && hasMinLength && hasLowerCaseCharacter && hasUpperCaseCharacter
}