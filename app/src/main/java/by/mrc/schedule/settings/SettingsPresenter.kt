package by.mrc.schedule.settings

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


    fun init() {
        settingsInteractor.groupNameUpdates()
            .defaultIfEmpty("<not defined>")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { name ->
                view.renderGroupName(name)
            }
    }

//    fun editButtonClicked() {
//        settingsInteractor.getGroup()
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSuccess { name ->
//                view.getDialogs().showChooseGroupDialog(name)
//                    .observeOn(Schedulers.io())
//                    .flatMapCompletable { newName ->
//                        settingsInteractor.setGroup(newName)
//                    }
//                    .subscribe()
//            }
//            .subscribe()
//    }

    fun editButtonClicked() {
        view.getDialogs().showChooseGroupDialog()
            .observeOn(Schedulers.io())
            .flatMapCompletable { newName ->
                settingsInteractor.setGroup(newName)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                view.hideBottomSheet()
            }
            .subscribe()
    }

}