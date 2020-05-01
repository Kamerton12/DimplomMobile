package by.mrc.shedule.pager

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.mrc.shedule.schedule.DayView
import by.mrc.shedule.schedule.Schedule

class ScheduleViewHolder(private val view: DayView) : RecyclerView.ViewHolder(view) {

    fun bind(schedule: List<Schedule>){
        view.renderSchedule(schedule)
    }

}