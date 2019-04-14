package com.kamer.aviasalestest.utils

import android.content.Context


class StringProvider(private val context: Context) {

    fun provide(resId: Int): String = context.getString(resId)

}