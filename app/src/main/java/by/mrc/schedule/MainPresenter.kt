package by.mrc.schedule

import by.mrc.schedule.settings.SettingsInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter(private val view: MainView) {

    @Inject
    lateinit var settingsInteractor: SettingsInteractor

    fun loadCurrentGroupAndUpdateTitle() {
        settingsInteractor.groupNameUpdates()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { name ->
                if (name.isBlank()) {
                    view.getDialogs().showChooseGroupDialog(false)
                        .observeOn(Schedulers.io())
                        .flatMapCompletable { newName ->
                            settingsInteractor.setGroup(newName)
                        }
                        .subscribeBy()
                } else {
                    view.setTitle("Расписание для группы $name")
                }
            }
            .subscribeBy()
    }
}