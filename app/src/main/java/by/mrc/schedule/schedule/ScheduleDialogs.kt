package by.mrc.schedule.schedule

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import by.mrc.schedule.R
import by.mrc.schedule.teacher.Teacher
import java.text.SimpleDateFormat
import java.util.*

private val dateFormatter = SimpleDateFormat("HH:mm", Locale.ROOT)

class ScheduleDialogs(private val context: Context) {

    fun showFullScheduleDialog(schedule: Schedule) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_schedule, null)
        val startTimeView = view.findViewById<TextView>(R.id.startTime)
        val endTimeView = view.findViewById<TextView>(R.id.endTime)
        val officeView = view.findViewById<TextView>(R.id.office)
        val disciplineView = view.findViewById<TextView>(R.id.lesson)
        val teacherView = view.findViewById<TextView>(R.id.teacher)

        startTimeView.text = dateFormatter.format(schedule.startTime)
        endTimeView.text = dateFormatter.format(schedule.endTime)
        officeView.text = schedule.office
        disciplineView.text = schedule.discipline.name
        teacherView.text = schedule.teacher.toLocalString()

        val dialog = AlertDialog.Builder(context).create()

        dialog.setView(view)
        dialog.show()
    }

    private fun Teacher.toLocalString(): String {
        return "$surname $name $patronymic"
    }
}