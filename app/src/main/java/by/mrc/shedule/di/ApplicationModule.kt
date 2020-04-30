package by.mrc.shedule.di

import android.content.Context
import by.mrc.shedule.schedule.ScheduleRepository
import okhttp3.OkHttpClient
import toothpick.config.Module

class ApplicationModule(private val context: Context) : Module() {
    init {
        bind(ScheduleRepository::class.java)
            .singleton()
        bind(OkHttpClient::class.java)
            .toInstance(OkHttpClient())
    }
}