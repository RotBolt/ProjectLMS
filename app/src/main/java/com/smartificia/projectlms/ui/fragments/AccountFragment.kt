package com.smartificia.projectlms.ui.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.smartificia.projectlms.R
import com.smartificia.projectlms.ui.activities.LoginActivity
import com.smartificia.projectlms.utils.GlobalLocalCache
import com.smartificia.projectlms.utils.Labels
import kotlinx.android.synthetic.main.fragment_account.*


/**
 * Fragment Showing the details of Current Logged in User
 *
 */
class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(GlobalLocalCache.getAccountInfo()) {
            tvPersonName.text = "$firstname $lastname"
            tvEmailAddress.text = "  $email"
        }

        btnLogOut.setOnClickListener {
            val sharedPrefs = context?.getSharedPreferences(Labels.sharedPrefs, Context.MODE_PRIVATE)
            sharedPrefs?.apply {
                edit().clear().apply()
                startActivity(Intent(context, LoginActivity::class.java))
            }


        }

    }
}
