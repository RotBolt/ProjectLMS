package com.smartificia.projectlms.ui.adapters

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.smartificia.projectlms.ui.fragments.AccountFragment
import com.smartificia.projectlms.ui.fragments.LeadsFragment
import com.smartificia.projectlms.ui.fragments.ReportsFragment

/**
 *  Adapter for Displaying the different Pages.
 */

class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    // returns the fragment as per current page number
    // modify this method to return required fragment/page as per page number
    override fun getItem(index: Int) = when (index) {
        0 -> LeadsFragment.newInstance()
        1 -> ReportsFragment.newInstance()
        else->AccountFragment()
    }

    // returns the total count of pages/fragments to be shown.
    // new page added then increase the count
    override fun getCount() = 3

}