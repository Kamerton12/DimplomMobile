package by.mrc.schedule.teacher

interface TeacherView {

    fun renderTeachers(teachers: List<Teacher>, fromCache: Boolean, stopSpinner: Boolean)

    fun showLoading()

}