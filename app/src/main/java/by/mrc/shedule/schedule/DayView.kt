package by.mrc.shedule.schedule

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import by.mrc.shedule.R
import by.mrc.shedule.teacher.Teacher
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_day.*
import kotlinx.android.synthetic.main.fragment_day.view.*
import toothpick.Toothpick
import java.util.*


class DayView(context: Context, attributeSet: AttributeSet? = null): FrameLayout(context, attributeSet) {

    private val adapter = ScheduleAdapter()

    init {
        LayoutInflater.from(context).inflate(R.layout.fragment_day, this)
        this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        lessons_recycler.adapter = adapter

    }

    fun renderSchedule(schedule: List<Schedule>) {
        adapter.populate(schedule)
    }
}