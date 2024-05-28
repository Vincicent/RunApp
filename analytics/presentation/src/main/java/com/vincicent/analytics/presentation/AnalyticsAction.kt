package com.vincicent.analytics.presentation

sealed interface AnalyticsAction {
    data object onBackClick: AnalyticsAction
}