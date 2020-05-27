package by.mrc.schedule.teacher.db

import androidx.room.*
import by.mrc.schedule.teacher.Teacher

@Dao
interface TeacherDao {
    @Query("SELECT * from teacher")
    fun getAllTeachers(): List<Teacher>

    @Transaction
    fun replaceAllTeachers(schedule: List<Teacher>) {
        deleteAll()
        insertAll(schedule)
    }

    @Query("DELETE FROM teacher")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(schedule: List<Teacher>)
}