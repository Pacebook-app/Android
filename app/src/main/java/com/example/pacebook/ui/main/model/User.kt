package com.example.pacebook.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val participantCount: Int,
    val profileImage: Int,
    val time: String,
    val pace: String,
    val intro: String,
    val isCreator: Boolean = false // 내가 만든 글인지 확인하는 변수
) : Parcelable