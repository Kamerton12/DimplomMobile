package by.mrc.schedule.schedule

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import by.mrc.schedule.discipline.Discipline
import by.mrc.schedule.teacher.Teacher
import com.google.gson.Gson
import io.reactivex.rxjava3.disposables.Disposable
import java.util.*

@Entity(tableName = "schedule")
data class Schedule(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "start_time")
    val startTime: Date,
    @ColumnInfo(name = "end_time")
    val endTime: Date,
    val discipline: Discipline,
    val teacher: Teacher,
    val office: String,
    val group: String
)

class DisciplineConverter {

    @TypeConverter
    fun fromDiscipline(discipline: Discipline): String {
        return Gson().toJson(discipline)
    }

    @TypeConverter
    fun toDiscipline(data: String): Discipline {
        return Gson().fromJson(data, Discipline::class.java)
    }
}

class TeacherConverter {

    @TypeConverter
    fun fromDiscipline(discipline: Teacher): String {
        return Gson().toJson(discipline)
    }

    @TypeConverter
    fun toDiscipline(data: String): Teacher {
        return Gson().fromJson(data, Teacher::class.java)
    }
}