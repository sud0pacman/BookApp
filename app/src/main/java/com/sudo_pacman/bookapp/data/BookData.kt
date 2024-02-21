package com.sudo_pacman.bookapp.data

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookData(
    val orderName: String, // tartib uchun nom
    val bookName: String,
    val authorName: String,
    val image: Bitmap
) : Parcelable