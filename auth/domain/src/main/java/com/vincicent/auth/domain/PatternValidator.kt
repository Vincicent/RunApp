package com.vincicent.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}