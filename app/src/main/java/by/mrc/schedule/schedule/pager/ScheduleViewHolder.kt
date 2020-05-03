package by.mrc.schedule.schedule.pager

import androidx.recyclerview.widget.RecyclerView
import by.mrc.schedule.schedule.DayView
import by.mrc.schedule.schedule.Schedule

class ScheduleViewHolder(private val view: DayView) : RecyclerView.ViewHolder(view) {

    fun bind(schedule: List<Schedule>){
        view.renderSchedule(schedule)
    }

}