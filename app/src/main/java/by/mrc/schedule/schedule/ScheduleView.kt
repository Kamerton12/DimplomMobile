package by.mrc.schedule.schedule

interface ScheduleView {

    fun renderSchedule(schedule: List<Schedule>)

    fun renderStartUpdate()
}