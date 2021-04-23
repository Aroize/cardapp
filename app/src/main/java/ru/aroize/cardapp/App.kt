package ru.aroize.cardapp

import android.app.Application
import ru.aroize.cardapp.domain.ApiInitializer
import ru.aroize.cardapp.presentation.AppContextHolder

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ApiInitializer.init()
        AppContextHolder.context = this
    }
}