package by.mrc.shedule.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.mrc.shedule.R
import by.mrc.shedule.teacher.Teacher
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_shedule.*
import toothpick.Scope
import toothpick.Toothpick
import java.util.*


class ScheduleFragment: Fragment(), ScheduleView {

    private val adapter = ScheduleAdapter()
    private val presenter = SchedulePresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Toothpick.inject(presenter, Toothpick.openScope("APP"))
        return layoutInflater.inflate(R.layout.fragment_shedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lessons_recycler.adapter = adapter
        adapter.populate(listOf(
            Schedule(Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342"),
            Schedule(Date(), Date(), "OAiP", Teacher("name", "surname", "patronymic", "+375438597349", "kek lol desc"), "310", "62342")
        ))

        swipeRefresh.setOnRefreshListener {
            presenter.updateSchedule()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEvent {
                    swipeRefresh.isRefreshing = false
                }
                .subscribe()
        }
    }

    override fun renderSchedule(schedule: List<Schedule>) {
        adapter.populate(schedule)
    }
}