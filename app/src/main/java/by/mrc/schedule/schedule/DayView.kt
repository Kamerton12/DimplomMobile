package by.mrc.schedule.schedule

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import by.mrc.schedule.R
import kotlinx.android.synthetic.main.fragment_day.view.*


class DayView(context: Context, clicks: (Int) -> Unit): FrameLayout(context, null) {

    private val adapter = ScheduleAdapter(clicks)

    init {
        LayoutInflater.from(context).inflate(R.layout.fragment_day, this)
        this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        lessons_recycler.adapter = adapter

    }

    fun renderSchedule(schedule: List<Schedule>) {
        adapter.populate(schedule)
    }
}