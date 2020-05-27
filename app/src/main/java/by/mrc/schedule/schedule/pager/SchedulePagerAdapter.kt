package by.mrc.schedule.schedule.pager

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.mrc.schedule.schedule.DayView
import by.mrc.schedule.schedule.Schedule

class SchedulePagerAdapter(val clicks: (Int) -> Unit) : RecyclerView.Adapter<ScheduleViewHolder>() {

    private var data: List<List<Schedule>> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(DayView(parent.context, clicks))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun populate(data: List<List<Schedule>>) {
        this.data = data
        notifyDataSetChanged()
    }
}
