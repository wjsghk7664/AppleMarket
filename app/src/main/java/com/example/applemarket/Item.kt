package com.example.applemarket

import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val num: Int,
    val image: String,
    val title: String,
    val introduce: String,
    val seller: String,
    val location: String,
    val price: String,
    var like: Int,
    val chat: Int,
    var checked: Boolean
) : Parcelable