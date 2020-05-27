package by.mrc.schedule.schedule.db

import androidx.room.*
import by.mrc.schedule.schedule.Schedule

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule")
    fun getAllSchedule(): List<Schedule>

    @Query("SELECT * FROM schedule WHERE id = :id")
    fun getById(id: Int): Schedule

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