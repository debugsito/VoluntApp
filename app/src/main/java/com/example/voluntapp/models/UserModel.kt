package com.example.voluntapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val name: String,
    val dni: String,
    val edad: String,
    val email: String,
    val resena: String,
    val uid: String,
    val perfil: String,
    val razon: String,
): Parcelable