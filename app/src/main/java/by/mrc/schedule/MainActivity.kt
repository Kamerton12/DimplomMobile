package by.mrc.schedule

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import by.mrc.schedule.schedule.ScheduleFragment
import by.mrc.schedule.teacher.TeachersFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_settings.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    private val fragmentTags = listOf(TeachersFragment.TAG, ScheduleFragment.TAG)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheet.visibility = View.GONE
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
                    bottomSheet.visibility = View.VISIBLE
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
            false
        }
        bottom_navigation.selectedItemId = R.id.lessons
    }

    private fun FragmentManager.openFragment(tag: String) {
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
}
