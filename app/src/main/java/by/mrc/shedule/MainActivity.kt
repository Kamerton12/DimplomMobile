package by.mrc.shedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.mrc.shedule.pager.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager.adapter = MainPagerAdapter(supportFragmentManager)
        pager.currentItem = 0
        tab_layout.setupWithViewPager(pager)
    }
}
