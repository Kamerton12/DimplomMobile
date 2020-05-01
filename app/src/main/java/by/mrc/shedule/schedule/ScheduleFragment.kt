package by.mrc.shedule.schedule

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.PagerTitleStrip
import by.mrc.shedule.R
import by.mrc.shedule.pager.SchedulePagerAdapter
import by.mrc.shedule.teacher.Teacher
import by.mrc.shedule.view.CustomPagerTitleStrip
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_schedule.*
import toothpick.Toothpick
import java.util.*

class ScheduleFragment : Fragment(), ScheduleView {

    private lateinit var adapter: SchedulePagerAdapter
    private val presenter = SchedulePresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toothpick.inject(presenter, Toothpick.openScope("APP"))
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SchedulePagerAdapter()
        pager.adapter = adapter

        fixSensitivity(swipeRefresh)

        swipeRefresh.setOnRefreshListener {
            presenter.updateSchedule()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEvent {
                    swipeRefresh.isRefreshing = false
                }
                .subscribe()
        }
        val list = listOf(
            Schedule(1, Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(2, Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(3, Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(4, Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(5, Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(6, Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(7, Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(8, Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(9, Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(0, Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342")
        )
        renderSchedule(list + list + list + list + list)

        setupStrip()
    }

    private fun setupStrip() {
        custom_strip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        custom_strip.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
        custom_strip.setBackgroundResource(R.color.base)
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

    override fun renderSchedule(schedule: List<Schedule>) {
        var i = 0
        val data = schedule.groupBy { (i++) / 2 }.values.toList()
        adapter.populate(data)

        custom_strip.setup(pager, object : CustomPagerTitleStrip.TitleProvider {
            override fun getName(id: Int): String {
                return data.getOrNull(id)?.firstOrNull()?.discipline ?: "id = $id"
            }

            override fun getSize(): Int = data.size

        })
    }
}