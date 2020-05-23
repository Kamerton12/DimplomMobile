package by.mrc.schedule.schedule

import by.mrc.schedule.discipline.Discipline
import by.mrc.schedule.teacher.Teacher
import io.reactivex.rxjava3.disposables.Disposable
import java.util.*

data class Schedule(
    val id: Int,
    val startTime: Date,
    val endTime: Date,
    val discipline: Discipline,
    val teacher: Teacher,
    val office: String,
    val group: String
)