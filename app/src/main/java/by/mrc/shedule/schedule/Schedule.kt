package by.mrc.shedule.schedule

import by.mrc.shedule.teacher.Teacher
import java.util.*

data class Schedule(
    val id: Int,
    val startTime: Date,
    val endTime: Date,
    val discipline: String,
    val teacher: Teacher,
    val office: String,
    val group: String
)