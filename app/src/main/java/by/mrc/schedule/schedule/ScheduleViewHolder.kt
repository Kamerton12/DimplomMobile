package by.mrc.schedule.schedule

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.mrc.schedule.R
import by.mrc.schedule.teacher.Teacher
import java.text.SimpleDateFormat
import java.util.*


private val dateFormatter = SimpleDateFormat("HH:mm", Locale.ROOT)

class ScheduleViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val startTimeView = view.findViewById<TextView>(R.id.startTime)
    private val endTimeView = view.findViewById<TextView>(R.id.endTime)
    private val officeView = view.findViewById<TextView>(R.id.office)
    private val disciplineView = view.findViewById<TextView>(R.id.lesson)
    private val teacherView = view.findViewById<TextView>(R.id.teacher)

    fun bind(schedule: Schedule, click: () -> Unit) {
        startTimeView.text = dateFormatter.format(schedule.startTime)
        endTimeView.text = dateFormatter.format(schedule.endTime)
        officeView.text = schedule.office
        disciplineView.text = schedule.discipline.shortName
        teacherView.text = schedule.teacher.toLocalString()
        itemView.setOnClickListener { click() }
    }

    private fun Teacher.toLocalString(): String {
        val nameChar = if(name.isNotBlank()) name.trim()[0] + ". " else ""
        val surnameChar = if(patronymic.isNotBlank()) patronymic.trim()[0] + "." else ""
        return "$surname $nameChar$surnameChar"
    }
}