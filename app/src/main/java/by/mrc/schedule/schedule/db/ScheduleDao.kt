package by.mrc.schedule.schedule.db

import androidx.room.*
import by.mrc.schedule.schedule.Schedule

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule")
    fun getAllSchedule(): List<Schedule>

    @Transaction
    fun replaceAllSchedule(schedule: List<Schedule>) {
        deleteAll()
        insertAll(schedule)
    }

    @Query("DELETE FROM schedule")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(schedule: List<Schedule>)
}