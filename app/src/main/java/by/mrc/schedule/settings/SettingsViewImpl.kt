package by.mrc.schedule.settings

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import by.mrc.schedule.MainDialogs
import by.mrc.schedule.MainView
import by.mrc.schedule.R
import by.mrc.schedule.tools.dp
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.view_settings.view.*
import toothpick.Toothpick


class SettingsViewImpl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), SettingsView {

    private val presenter = SettingsPresenter(this)

    init {
        Toothpick.inject(presenter, Toothpick.openScope("APP"))
        LayoutInflater.from(context).inflate(R.layout.view_settings, this)
        setBackgroundResource(R.color.colorPrimaryDark)
        setPadding(0, 0, 0, 16.dp.toInt())
        presenter.init()
        editButton.setOnClickListener {
            presenter.editButtonClicked()
        }
    }

    private fun requireMainView(): MainView {
        return context as MainView
    }

    override fun renderGroupName(name: String) {
        groupName.text = name
    }

    override fun getDialogs(): MainDialogs {
        return requireMainView().getDialogs()
    }

    override fun hideBottomSheet() {
        requireMainView().hideBottomSheet()
    }
}