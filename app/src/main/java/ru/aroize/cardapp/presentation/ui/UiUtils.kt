package ru.aroize.cardapp.presentation

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun Context.toActivity(): Activity {
    var context = this
    while (context !is Activity && context is ContextWrapper) context = context.baseContext
    return context as Activity
}