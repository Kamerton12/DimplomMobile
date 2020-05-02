package by.mrc.schedule.schedule

import by.mrc.schedule.settings.SettingsInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SchedulePresenter(
    private val view: ScheduleView
) {
    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    @Inject
    lateinit var settingsInteractor: SettingsInteractor

    fun updateSchedule(): Completable {
        return scheduleUpdates().firstOrError().ignoreElement()
    }

    fun scheduleUpdates(): Observable<Any> {
        return settingsInteractor.groupNameUpdates()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { view.renderStartUpdate() }
            .observeOn(Schedulers.io())
            .flatMapSingle { name ->
                scheduleRepository.getSchedule(name)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                view.renderSchedule(it)
            }
            .map { Any() }
    }
}