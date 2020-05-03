package by.mrc.schedule.teacher.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.mrc.schedule.R
import by.mrc.schedule.teacher.Teacher

class TeacherAdapter : RecyclerView.Adapter<TeacherViewHolder>() {

    private var data: List<Teacher> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        return TeacherViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_teacher, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun populate(data: List<Teacher>) {
        this.data = data
        notifyDataSetChanged()
    }

}