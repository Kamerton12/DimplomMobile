package by.mrc.schedule

import android.app.Application
import by.mrc.schedule.di.ApplicationModule
import toothpick.Toothpick

class ScheduleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val scope = Toothpick.openScope("APP")
        scope.installModules(ApplicationModule(applicationContext))
    }
}