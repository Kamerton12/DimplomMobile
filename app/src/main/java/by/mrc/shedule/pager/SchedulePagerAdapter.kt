package by.mrc.shedule.pager

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.mrc.shedule.schedule.DayView
import by.mrc.shedule.schedule.Schedule

class SchedulePagerAdapter : RecyclerView.Adapter<ScheduleViewHolder>() {

    private var data: List<List<Schedule>> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(DayView(parent.context))
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
