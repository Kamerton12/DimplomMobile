package by.mrc.schedule.teacher

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

class TeacherPresenter(private val view: TeacherView) {

    @Inject
    lateinit var teacherRepository: TeacherRepository

    private val queryUpdates = BehaviorSubject.createDefault("")
    private val teacherUpdates = BehaviorSubject.createDefault<List<Teacher>>(emptyList())

    fun wireUpdates() {
        Observables.combineLatest(queryUpdates, teacherUpdates) { query, teachers ->
            teachers.filter { it.filter(query) }
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                view.showLoading()
            }
            .subscribe { teachers ->
                view.renderTeachers(teachers)
            }
    }

    fun queryTextChanged(query: String) {
        queryUpdates.onNext(query)
    }

    fun updateTeachers() {
        teacherRepository.getTeacher()
            .subscribe { teachers ->
                teacherUpdates.onNext(teachers)
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