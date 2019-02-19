package com.smartificia.projectlms.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.smartificia.projectlms.R
import com.smartificia.projectlms.ui.adapters.PagerAdapter
import com.smartificia.projectlms.utils.GlobalLocalCache
import com.smartificia.projectlms.utils.Labels
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            R.id.action_leads -> {
                toolbar.title = "Hello, ${GlobalLocalCache.getAccountInfo().firstname}"
                containerFrame.currentItem = 0
                true
            }
            R.id.action_reports -> {
                toolbar.title = Labels.reports
                containerFrame.currentItem = 1
                true
            }
            R.id.action_account -> {
                toolbar.title = Labels.accountInfo
                containerFrame.currentItem = 2
                true
            }
            else -> false

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupComponents()
    }

    private fun setupComponents() {

        toolbar.title = "Hello, ${GlobalLocalCache.getAccountInfo().firstname}"
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        setSupportActionBar(toolbar)
        containerFrame.adapter = PagerAdapter(supportFragmentManager)
        containerFrame.offscreenPageLimit = 2
        containerFrame.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {}

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

            override fun onPageSelected(index: Int) {
                when (index) {
                    0 -> {
                        bottomNavigationView.selectedItemId = R.id.action_leads
                        toolbar.title = "Hello, ${GlobalLocalCache.getAccountInfo().firstname}"
                    }
                    1 -> {
                        bottomNavigationView.selectedItemId = R.id.action_reports
                        toolbar.title = Labels.reports
                    }
                    2 -> {
                        bottomNavigationView.selectedItemId = R.id.action_account
                        toolbar.title = Labels.accountInfo
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            R.id.action_filter -> {
                startActivity(Intent(this, CalendarFilterActivity::class.java))
                true
            }
            else -> false
        }
    }


}
