package by.mrc.schedule.settings

import android.annotation.SuppressLint
import android.content.SharedPreferences
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class SettingsRepository(private val preferences: SharedPreferences) {

    private val registerOnSharedPreferenceChangeListenerList: MutableList<SharedPreferences.OnSharedPreferenceChangeListener> = mutableListOf()

    @SuppressLint("ApplySharedPref")
    fun setGroupName(name: String): Completable {
        return Completable.fromAction {
            preferences.edit().putString(PARAM_GROUP, name).commit()
        }
            .subscribeOn(Schedulers.io())
    }

    fun getGroupName(): Single<String> {
        return Single.fromCallable<String> {
            preferences.getString(PARAM_GROUP, "")
        }
            .subscribeOn(Schedulers.io())
    }

    fun groupNameUpdates(): Observable<String> {
        return Observable.create<String> { emitter ->
            registerOnSharedPreferenceChangeListenerList.add(
                SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->
                    getGroupName()
                        .subscribeBy { name ->
                            emitter.onNext(name)
                        }
                }
            )
            preferences.registerOnSharedPreferenceChangeListener(
                registerOnSharedPreferenceChangeListenerList.last()
            )
        }
            .subscribeOn(Schedulers.io())
    }

    companion object {
        private const val PARAM_GROUP = "group_name"
    }
}