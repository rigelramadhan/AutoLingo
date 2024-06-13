package one.reevdev.autolingo.core.data.utils

import com.google.gson.Gson

fun <T> String.toKotlin(kClass: Class<T>): T = Gson().fromJson(this, kClass)