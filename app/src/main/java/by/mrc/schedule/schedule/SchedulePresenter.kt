package by.mrc.schedule.schedule

import android.util.Log
import by.mrc.schedule.settings.SettingsInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SchedulePresenter(
    private val view: ScheduleView
) {
    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    @Inject
    lateinit var settingsInteractor: SettingsInteractor

    val clicks: (Int) -> Unit = {
        scheduleRepository.getScheduleByIdFromCache(it)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { schedule ->
                view.scheduleDialogs().showFullScheduleDialog(schedule)
            }
            .subscribeBy()
    }

    fun updateSchedule(): Completable {
        return settingsInteractor.getGroup()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { view.renderStartUpdate() }
            .observeOn(Schedulers.io())
            .flatMap { name ->
                scheduleRepository.getSchedule(name)
            }
            .map { it.first to (it.second to true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                view.renderSchedule(it.first, it.second.first, it.second.second)
            }
            .ignoreElement()
    }

    fun scheduleUpdates(): Observable<Any> {
        return Observable.concat(
            scheduleRepository.loadFromCache().map { it to (false to false) }.toObservable(),
            settingsInteractor.groupNameUpdates()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { view.renderStartUpdate() }
                .observeOn(Schedulers.io())
                .flatMapSingle { name ->
                    scheduleRepository.getSchedule(name)
                }
                .map { it.first to (it.second to true) }
        )
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                view.renderSchedule(it.first, it.second.first, it.second.second)
            }
            .map { Any() }
    }
}