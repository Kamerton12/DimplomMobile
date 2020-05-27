package by.mrc.schedule.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.mrc.schedule.R

class ScheduleAdapter(val clicks: (Int) -> Unit) : RecyclerView.Adapter<ScheduleViewHolder>() {

    private val list: MutableList<Schedule> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_lesson, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(list[position]) { clicks(list[position].id) }
    }

    fun populate(data: List<Schedule>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }
}