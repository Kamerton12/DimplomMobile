package by.mrc.schedule

interface MainView {
    fun getDialogs(): MainDialogs
    fun setTitle(text: String)
    fun hideBottomSheet()
}