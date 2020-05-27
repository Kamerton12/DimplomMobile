package by.mrc.schedule.schedule

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.mrc.schedule.BuildConfig
import by.mrc.schedule.R
import by.mrc.schedule.schedule.pager.SchedulePagerAdapter
import by.mrc.schedule.teacher.Teacher
import by.mrc.schedule.view.CustomPagerTitleStrip
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import toothpick.Toothpick
import java.text.SimpleDateFormat
import java.util.*

class ScheduleFragment : Fragment(), ScheduleView {

    private lateinit var adapter: SchedulePagerAdapter
    private val presenter = SchedulePresenter(this)

    private val formatter = SimpleDateFormat("EEEE dd.MM", Locale.getDefault())
    private val formatterYear = SimpleDateFormat("EEEE dd.MM.yyyy", Locale.getDefault())

    private var firstTime = true

    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toothpick.inject(presenter, Toothpick.openScope("APP"))
        presenter.scheduleUpdates()
            .subscribeBy()
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SchedulePagerAdapter()
        pager.adapter = adapter

        fixSensitivity(swipeRefresh)

        swipeRefresh.setOnRefreshListener {
            presenter.updateSchedule()
                .subscribeBy()
        }
        setupStrip()
    }

    private fun setupStrip() {
        custom_strip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        custom_strip.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
        custom_strip.setNonPrimaryAlpha(0.4f)
    }

    private fun fixSensitivity(layout: SwipeRefreshLayout) {
        val f = SwipeRefreshLayout::class.java.getDeclaredField("mTouchSlop")
        f.isAccessible = true
        f.setInt(layout, f.getInt(layout) * 100)
    }

    companion object {
        const val TAG = "ScheduleFragment"
    }

    override fun renderSchedule(schedule: List<Schedule>, fromCache: Boolean, stopSpinner: Boolean) {
        if(stopSpinner) {
            swipeRefresh.isRefreshing = false
        }
        val today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val dataMap = schedule.groupBy {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = it.startTime.time
            }
            calendar.get(Calendar.DAY_OF_YEAR)
        }.values
        var idToday = 0
        val data = dataMap.toList().sortedBy { it[0].startTime }
        for (i in data.indices) {
            val calendar = Calendar.getInstance().apply {
                timeInMillis = data[i][0].startTime.time
            }
            val day = calendar.get(Calendar.DAY_OF_YEAR)
            if (day == today) {
                idToday = i
                break
            }
        }
        adapter.populate(data)
        if (data.isEmpty()) {
            pager.visibility = View.GONE
        } else {
            pager.visibility = View.VISIBLE
        }

        custom_strip.setup(pager, object : CustomPagerTitleStrip.TitleProvider {

            override fun getName(id: Int): String {
                return data.getOrNull(id)?.getOrNull(0)?.startTime.format()
            }

            override fun getSize(): Int = data.size
        })
        if (firstTime) {
            firstTime = false
            pager.setCurrentItem(idToday, false)
        }
        snackbar?.dismiss()
        if (fromCache) {
            snackbar = Snackbar
                .make(coordinator, "Не удалось обновить расписание", Snackbar.LENGTH_LONG)
            snackbar?.view?.setBackgroundColor(Color.RED)
            snackbar?.view?.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.textSize =
                18f
            snackbar?.show()
        }
    }

    override fun renderStartUpdate() {
        swipeRefresh.isRefreshing = true
    }

    private fun Date?.format(): String {
        this ?: return ""
        val calendar = Calendar.getInstance().apply {
            timeInMillis = this@format.time
        }
        val month = calendar.get(Calendar.MONTH)
        return if (BuildConfig.DEBUG || month == 11 || month == 0) {
            formatterYear.format(this).capitalize(Locale.getDefault())
        } else {
            formatter.format(this).capitalize(Locale.getDefault())
        }
    }

    private fun String.capitalize(locale: Locale): String {
        if (isNotEmpty()) {
            val firstChar = this[0]
            if (firstChar.isLowerCase()) {
                return buildString {
                    val titleChar = firstChar.toTitleCase()
                    if (titleChar != firstChar.toUpperCase()) {
                        append(titleChar)
                    } else {
                        append(this@capitalize.substring(0, 1).toUpperCase(locale))
                    }
                    append(this@capitalize.substring(1))
                }
            }
        }
        return this
    }
}