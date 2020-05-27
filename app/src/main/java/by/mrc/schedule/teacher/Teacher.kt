package by.mrc.schedule.teacher

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teacher")
data class Teacher(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val surname: String,
    val patronymic: String,
    val phoneNumber: String,
    val description: String
)