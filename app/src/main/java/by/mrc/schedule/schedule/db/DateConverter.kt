package by.mrc.schedule.schedule.db

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(data: Long): Date {
        return Date(data)
    }
}