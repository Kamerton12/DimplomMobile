package by.mrc.schedule

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.mrc.schedule.schedule.DisciplineConverter
import by.mrc.schedule.schedule.Schedule
import by.mrc.schedule.schedule.TeacherConverter
import by.mrc.schedule.schedule.db.DateConverter
import by.mrc.schedule.schedule.db.ScheduleDao
import by.mrc.schedule.teacher.Teacher
import by.mrc.schedule.teacher.db.TeacherDao


@Database(entities = [
    Schedule::class,
    Teacher::class
], version = 1)
@TypeConverters(
    DateConverter::class,
    TeacherConverter::class,
    DisciplineConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
    abstract fun teacherDao(): TeacherDao
}