package com.example.androidnavigationimplementation.common.data

import com.example.androidnavigationimplementation.common.Result
import com.example.androidnavigationimplementation.common.data.model.AuthUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class AuthRepository(val dataSource: AuthDataSource) {

    var user: AuthUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(username: String, password: String): Result<AuthUser> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setAuthUser(result.data)
        }

        return result
    }

    fun signup(fullname: String, username: String, password: String): Result<AuthUser> {
        // handle sign up
        val result = dataSource.signup(fullname, username, password)

        if (result is Result.Success) {
            setAuthUser(result.data)
        }

        return result
    }

    private fun setAuthUser(authUser: AuthUser) {
        this.user = authUser
    }
}