package by.mrc.schedule.di

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.room.Room
import by.mrc.schedule.AppDatabase
import by.mrc.schedule.schedule.ScheduleRepository
import by.mrc.schedule.schedule.db.ScheduleDao
import by.mrc.schedule.settings.SettingsRepository
import by.mrc.schedule.teacher.db.TeacherDao
import okhttp3.OkHttpClient
import toothpick.config.Module

class ApplicationModule(private val context: Context) : Module() {
    init {
        bind(Context::class.java)
            .toInstance(context)
        bind(ScheduleRepository::class.java)
            .singleton()
        bind(OkHttpClient::class.java)
            .toInstance(OkHttpClient())
        val database = Room.databaseBuilder(context, AppDatabase::class.java, "main_database")
            .build()
        bind(AppDatabase::class.java)
            .toInstance(database)
        bind(ScheduleDao::class.java)
            .toInstance(database.scheduleDao())
        bind(TeacherDao::class.java)
            .toInstance(database.teacherDao())

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        bind(SettingsRepository::class.java)
            .toInstance(SettingsRepository(sharedPreferences))
    }
}