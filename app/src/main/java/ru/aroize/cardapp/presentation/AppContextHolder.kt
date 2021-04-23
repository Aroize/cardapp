package ru.aroize.cardapp.presentation

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("StaticFieldLeak")
object AppContextHolder {
    lateinit var context: Context
}