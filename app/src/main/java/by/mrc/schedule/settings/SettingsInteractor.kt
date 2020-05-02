package by.mrc.schedule.settings

import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

class SettingsInteractor @Inject constructor(private val repository: SettingsRepository) {

    fun getGroup(): Single<String> {
        return repository.getGroupName()
    }

    fun setGroup(name: String): Completable {
        return repository.setGroupName(name)
    }

    fun groupNameUpdates(): Observable<String> {
        return Observable.concat(repository.getGroupName().toObservable(), repository.groupNameUpdates())
            .subscribeOn(Schedulers.io())
    }

}