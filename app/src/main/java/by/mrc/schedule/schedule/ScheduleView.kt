package by.mrc.schedule.schedule

interface ScheduleView {

    fun renderSchedule(schedule: List<Schedule>, fromCache: Boolean, stopSpinner: Boolean)

    fun renderStartUpdate()

    fun showToast(text: String)

    fun scheduleDialogs(): ScheduleDialogs
}