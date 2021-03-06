package com.example.androidnavigationimplementation.common.data.model

/**
 * Data class that captures user information for Authenticated users retrieved from AuthRepository
 */
data class AuthUser(
    val userId: String,
    val displayName: String,
)