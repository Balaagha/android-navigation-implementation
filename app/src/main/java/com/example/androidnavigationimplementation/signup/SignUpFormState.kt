package com.example.androidnavigationimplementation.signup

/**
 * Data validation state of the sign-up form.
 */
data class SignUpFormState(
    val fullnameError: Int? = null,
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false,
)