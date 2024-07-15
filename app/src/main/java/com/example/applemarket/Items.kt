package com.example.applemarket

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.core.graphics.drawable.toBitmap

object Items {
    val items = ArrayList<Item>()

    val likes = intArrayOf(12, 8, 23, 14, 22, 25, 142, 31, 7, 40)
    val chats = intArrayOf(25, 28, 5, 17, 9, 16, 54, 7, 28, 6)

    fun inits(context: Context) {
        for (i in 1..10) {
            val image = "sample${i}"
            val titleId = context.resources.getIdentifier(
                "item_sample${i}_title",
                "string",
                context.packageName
            )
            val title = context.resources.getString(titleId)
            val introduceId = context.resources.getIdentifier(
                "item_sample${i}_introduce",
                "string",
                context.packageName
            )
            val introduce = context.resources.getString(introduceId)
            val sellerId = context.resources.getIdentifier(
                "item_sample${i}_seller",
                "string",
                context.packageName
            )
            val seller = context.resources.getString(sellerId)
            val locationID = context.resources.getIdentifier(
                "item_sample${i}_location",
                "string",
                context.packageName
            )
            val location = context.resources.getString(locationID)
            val priceId = context.resources.getIdentifier(
                "item_sample${i}_price",
                "string",
                context.packageName
            )
            val price = context.resources.getString(priceId)
            items += Item(
                i,
                image,
                title,
                introduce,
                seller,
                location,
                Convert(price),
                likes[i - 1],
                chats[i - 1],
                false
            )
        }
    }

    fun Convert(s: String): String {
        val tmp = StringBuilder()
        tmp.append("원")
        for ((i, v) in s.reversed().withIndex()) {
            if (i % 3 == 0 && i != 0) tmp.append(',')
            tmp.append(v)
        }
        return tmp.reversed().toString()
    }
}