package by.mrc.schedule.schedule

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class SchedulePresenter(
    private val scheduleView: ScheduleView
) {
    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    fun updateSchedule(): Completable {
        return scheduleRepository.getSchedule()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                scheduleView.renderSchedule(it)
            }
            .ignoreElement()
    }
}