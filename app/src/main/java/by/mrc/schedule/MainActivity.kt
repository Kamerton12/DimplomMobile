package by.mrc.schedule

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import by.mrc.schedule.schedule.ScheduleFragment
import by.mrc.schedule.teacher.TeachersFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_settings.*
import toothpick.Toothpick
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity(), MainView {

    private val fragmentTags = listOf(TeachersFragment.TAG, ScheduleFragment.TAG)
    private val dialogs = MainDialogs(this)
    private val presenter = MainPresenter(this)
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    @Volatile
    private var canChangeTitle = true
    private var lastSavedTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(dialogs, Toothpick.openScope("APP"))
        Toothpick.inject(presenter, Toothpick.openScope("APP"))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
//                    bottomSheet.visibility = View.GONE
                }
            }

        })
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.lessons -> {
                    supportFragmentManager.openFragment(ScheduleFragment.TAG)
                    item.isChecked = true
                }
                R.id.teachers -> {
                    supportFragmentManager.openFragment(TeachersFragment.TAG)
                    item.isChecked = true
                }
                R.id.settings -> {
//                    bottomSheet.visibility = View.VISIBLE
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
            false
        }
        bottom_navigation.selectedItemId = R.id.teachers
        presenter.loadCurrentGroupAndUpdateTitle()
    }

    private fun FragmentManager.openFragment(tag: String) {
        when (tag) {
            TeachersFragment.TAG -> title = "Преподаватели"
            ScheduleFragment.TAG -> title = lastSavedTitle
        }
        canChangeTitle = ScheduleFragment.TAG == tag
        val transaction = beginTransaction()
        val foundFragment = findFragmentByTag(tag)
        if (foundFragment == null) {
            val fragment = when (tag) {
                TeachersFragment.TAG -> TeachersFragment()
                ScheduleFragment.TAG -> ScheduleFragment()
                else -> throw IllegalArgumentException()
            }
            transaction.add(R.id.fragmentHolder, fragment, tag)
        } else {
            transaction.show(foundFragment)
        }
        for (fragmentTag in fragmentTags) {
            if (fragmentTag != tag) {
                findFragmentByTag(fragmentTag)?.let { transaction.hide(it) }
            }
        }
        transaction.commitNow()
    }

    override fun getDialogs(): MainDialogs {
        return dialogs
    }

    override fun setTitle(text: String) {
        lastSavedTitle = text
        if(canChangeTitle) {
            title = text
        }
    }

    override fun hideBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}
