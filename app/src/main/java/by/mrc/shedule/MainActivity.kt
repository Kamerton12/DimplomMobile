package by.mrc.shedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import by.mrc.shedule.schedule.ScheduleFragment
import by.mrc.shedule.teacher.TeachersFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    private val fragmentTags = listOf(TeachersFragment.TAG, ScheduleFragment.TAG)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        pager.adapter = MainPagerAdapter(supportFragmentManager)
//        pager.currentItem = 0
//        pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {}
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {}
//
//            override fun onPageSelected(position: Int) {
//                when(position) {
//                    0 -> bottom_navigation.selectedItemId = R.id.lessons
//                    1 -> bottom_navigation.selectedItemId = R.id.teachers
//                    2 -> bottom_navigation.selectedItemId = R.id.settings
//                }
//            }
//
//        })
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.lessons -> {
                    supportFragmentManager.openFragment(ScheduleFragment.TAG)
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragmentHolder, ScheduleFragment()).commitNow()
                    item.isChecked = true
                }
                R.id.teachers -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragmentHolder, TeachersFragment()).commitNow()
                    supportFragmentManager.openFragment(TeachersFragment.TAG)
                    item.isChecked = true
                }
                R.id.settings -> {
                    Toast.makeText(this, "make later", Toast.LENGTH_SHORT).show()
//                    pager.setCurrentItem(2, true)
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
            val fragment = when(tag) {
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
