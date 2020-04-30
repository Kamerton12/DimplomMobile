package by.mrc.shedule.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.mrc.shedule.R

class ScheduleAdapter : RecyclerView.Adapter<ScheduleViewHolder>() {

    private val list: MutableList<Schedule> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun populate(data: List<Schedule>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}