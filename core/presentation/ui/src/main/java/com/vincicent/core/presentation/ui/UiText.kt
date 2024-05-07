package com.vincicent.core.presentation.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

sealed interface UiText {
    data class DynamicString(val value: String): UiText
    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ): UiText

    @Composable
    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}