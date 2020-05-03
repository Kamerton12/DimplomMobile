package by.mrc.schedule.teacher.recycler

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.mrc.schedule.R
import by.mrc.schedule.teacher.Teacher

class TeacherViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val fio = view.findViewById<TextView>(R.id.fio)
    private val phoneNumber = view.findViewById<TextView>(R.id.phoneNumber)
    private val description = view.findViewById<TextView>(R.id.anotherInfo)

    @SuppressLint("SetTextI18n")
    fun bind(data: Teacher) {
        fio.text = "${data.surname} ${data.name} ${data.patronymic}"
        phoneNumber.text = data.phoneNumber
        description.text = data.description
    }
}