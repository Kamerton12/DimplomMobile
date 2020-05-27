package by.mrc.schedule.teacher

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject

class TeacherPresenter(private val view: TeacherView) {

    @Inject
    lateinit var teacherRepository: TeacherRepository

    private val queryUpdates = BehaviorSubject.createDefault("")
    private val teacherUpdates = BehaviorSubject.createDefault(emptyList<Teacher>() to (false to false))

    @Volatile
    private var queryUpdate = true

    fun wireUpdates() {
        Observables.combineLatest(
            queryUpdates.doOnNext { queryUpdate = true },
            teacherUpdates.doOnNext { queryUpdate = false }
        ) { query, teachers ->
            teachers.first.filter { it.filter(query) } to ((!queryUpdate && teachers.second.first) to teachers.second.second)
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                view.showLoading()
            }
            .subscribe { teachers ->
                view.renderTeachers(teachers.first, teachers.second.first, teachers.second.second)
            }
    }

    fun queryTextChanged(query: String) {
        queryUpdates.onNext(query)
    }

    fun updateTeachers() {
        teacherRepository.getTeachersFromCache()
            .subscribe { teachers ->
                teacherUpdates.onNext(teachers to (false to false))
            }
        teacherRepository.getTeachers()
            .subscribe { teachers ->
                teacherUpdates.onNext(teachers.first to (teachers.second to true))
            }
    }

    private fun Teacher.filter(query: String): Boolean {
        val queryLowerCase = query.toLowerCase(Locale.getDefault())
        if("$surname $name $patronymic".toLowerCase(Locale.getDefault()).indexOf(queryLowerCase) != -1) {
            return true
        }
        if(phoneNumber.toLowerCase(Locale.getDefault()).indexOf(queryLowerCase) != -1) {
            return true
        }
        if(description.toLowerCase(Locale.getDefault()).indexOf(queryLowerCase) != -1) {
            return true
        }
        return false
    }
}