package com.example.androidnavigationimplementation.welcome

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfoModel(
    val userName: String
): Parcelable
