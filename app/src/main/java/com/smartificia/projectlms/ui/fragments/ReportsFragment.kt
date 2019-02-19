package com.smartificia.projectlms.ui.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.smartificia.projectlms.R
import com.smartificia.projectlms.ui.activities.FilteredLeadsActivity
import com.smartificia.projectlms.ui.viewModels.ReportsViewModel
import com.smartificia.projectlms.utils.Labels
import kotlinx.android.synthetic.main.reports_fragment.*

class ReportsFragment : Fragment() {

    companion object {
        fun newInstance() = ReportsFragment()
    }

    private lateinit var viewModel: ReportsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reports_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ReportsViewModel::class.java)
        // TODO: Use the ViewModel

        setUpViews()

    }

    private fun setUpViews() {
        tvStatusNew.setOnClickListener {
            startActivity(Intent(context, FilteredLeadsActivity::class.java).apply {
                putExtra(Labels.statusType, Labels.statusNew)
            })
        }

        tvStatusEmailSent.setOnClickListener {
            startActivity(Intent(context, FilteredLeadsActivity::class.java).apply {
                putExtra(Labels.statusType, Labels.statusEmail)
            })
        }

        tvStatusFollowUp.setOnClickListener {
            startActivity(Intent(context, FilteredLeadsActivity::class.java).apply {
                putExtra(Labels.statusType, Labels.statusFollowUp)
            })
        }

        tvStatusClosed.setOnClickListener {
            startActivity(Intent(context, FilteredLeadsActivity::class.java).apply {
                putExtra(Labels.statusType, Labels.statusClosed)
            })
        }

        tvStatusConverted.setOnClickListener {
            startActivity(Intent(context, FilteredLeadsActivity::class.java).apply {
                putExtra(Labels.statusType, Labels.statusConverted)
            })
        }
    }

}
