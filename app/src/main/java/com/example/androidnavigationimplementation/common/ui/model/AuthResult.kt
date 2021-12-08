package com.example.androidnavigationimplementation.common.ui.model

/**
 * Authentication result : success (user details) or error message.
 */
data class AuthResult(
    val success: AuthUserView? = null,
    val error: Int? = null
)