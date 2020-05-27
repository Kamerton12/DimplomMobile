package by.mrc.schedule.settings

import by.mrc.schedule.schedule.db.ScheduleDao
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SettingsPresenter(private val view: SettingsView) {

    @Inject
    lateinit var settingsInteractor: SettingsInteractor

    @Inject
    lateinit var scheduleDao: ScheduleDao

    fun init() {
        settingsInteractor.groupNameUpdates()
            .defaultIfEmpty("<not defined>")
            .observeOn(Schedulers.io())
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { name ->
                view.renderGroupName(name)
            }
    }

    fun editButtonClicked() {
        view.hideBottomSheet()
        view.getDialogs().showChooseGroupDialog()
            .observeOn(Schedulers.io())
            .flatMapCompletable { newName ->
                settingsInteractor.setGroup(newName)
            }
            .doOnComplete { scheduleDao.deleteAll() }
            .subscribeBy()
    }

}