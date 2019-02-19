package com.smartificia.projectlms.ui.adapters

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.smartificia.projectlms.ui.fragments.AccountFragment
import com.smartificia.projectlms.ui.fragments.LeadsFragment
import com.smartificia.projectlms.ui.fragments.ReportsFragment

class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(index: Int) = when (index) {
        0 -> LeadsFragment.newInstance()
        1 -> ReportsFragment.newInstance()
        else->AccountFragment()
    }

    override fun getCount() = 3

}