package by.mrc.schedule.settings

import by.mrc.schedule.MainDialogs

interface SettingsView {
    fun renderGroupName(name: String)
    fun getDialogs(): MainDialogs
    fun hideBottomSheet()
}