package com.example.androidnavigationimplementation.common.data

import com.example.androidnavigationimplementation.common.Result
import com.example.androidnavigationimplementation.common.data.model.AuthUser
import java.io.IOException
import java.util.*

/**
 * Class that handles authentication with login credentials / Sign up of user and retrieves user
 * information.
 */
class AuthDataSource {

    fun login(username: String, password: String): Result<AuthUser> {
        return try {
            // TODO: handle loggedInUser authentication
            val fakeUser = AuthUser(UUID.randomUUID().toString(), username)
            Result.Success(fakeUser)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun signup(fullname: String, username: String, password: String): Result<AuthUser> {
        return try {
            // TODO: handle User Sign-up
            val fakeUser = AuthUser(UUID.randomUUID().toString(), username)
            Result.Success(fakeUser)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}