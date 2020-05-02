package by.mrc.schedule.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import by.mrc.schedule.schedule.ScheduleRepository
import by.mrc.schedule.settings.SettingsRepository
import okhttp3.OkHttpClient
import toothpick.config.Module

class ApplicationModule(private val context: Context) : Module() {
    init {
        bind(ScheduleRepository::class.java)
            .singleton()
        bind(OkHttpClient::class.java)
            .toInstance(OkHttpClient())

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        bind(SettingsRepository::class.java)
            .toInstance(SettingsRepository(sharedPreferences))
    }
}