package by.mrc.shedule

import android.app.Application
import by.mrc.shedule.di.ApplicationModule
import toothpick.Toothpick

class ScheduleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val scope = Toothpick.openScope("APP")
        scope.installModules(ApplicationModule(applicationContext))
    }
}