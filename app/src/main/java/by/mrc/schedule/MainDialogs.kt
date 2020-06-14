package by.mrc.schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import by.mrc.schedule.schedule.groups.GroupRepository
import by.mrc.schedule.settings.SettingsInteractor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class MainDialogs(private val context: Context) {

    @Inject
    lateinit var groupRepository: GroupRepository

    @Inject
    lateinit var settingsInteractor: SettingsInteractor

    fun showChooseGroupDialog(cancelable: Boolean = true): Maybe<String> {
        return Maybe.create { emitter ->
            val dialogBuilder = MaterialAlertDialogBuilder(context, R.style.MyRounded_MaterialComponents_MaterialAlertDialog).create()
            val inflater = LayoutInflater.from(context)
            val dialogView = inflater.inflate(R.layout.dialog_editing_group_name, null)
            dialogBuilder.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            val editView = dialogView.findViewById<AppCompatAutoCompleteTextView>(R.id.edt_comment)
            groupRepository.getGroups()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {
                    if (it.isNotEmpty()) {
                        editView.hint = it[0]
                    }
                    editView.setAdapter(
                        ArrayAdapter(
                            context,
                            R.layout.simple_list_item,
                            it
                        )
                    )
                }
                .subscribe(
                    {}, {}
                )

            settingsInteractor.getGroup()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { groupName ->
                    editView.setText(groupName)
                }
            val groupNameWarningView = dialogView.findViewById<View>(R.id.groupNameWarning)
            val submitButton = dialogView.findViewById<Button>(R.id.buttonSubmit)
            val cancelButton = dialogView.findViewById<Button>(R.id.buttonCancel)
            if(!cancelable) {
                cancelButton.visibility = View.GONE
            }
            cancelButton.setOnClickListener { emitter.onComplete(); dialogBuilder.dismiss() }
            submitButton.setOnClickListener {
                if(editView.text.toString().isBlank()) {
                    groupNameWarningView.visibility = View.VISIBLE
                    return@setOnClickListener
                }
                emitter.onSuccess(editView.text.toString().trim())
                dialogBuilder.dismiss()
            }

            dialogBuilder.setCancelable(cancelable)
            dialogBuilder.setView(dialogView)
            dialogBuilder.show()
        }
    }
}