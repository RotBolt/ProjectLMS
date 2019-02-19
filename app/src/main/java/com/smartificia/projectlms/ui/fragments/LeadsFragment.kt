package com.smartificia.projectlms.ui.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.smartificia.projectlms.R
import com.smartificia.projectlms.api.ApiService
import com.smartificia.projectlms.models.GenericResponse
import com.smartificia.projectlms.models.LeadInfo
import com.smartificia.projectlms.ui.adapters.LeadsAdapter
import com.smartificia.projectlms.ui.viewModels.LeadsViewModel
import com.smartificia.projectlms.utils.GlobalLocalCache
import com.smartificia.projectlms.utils.convertToDatedList
import com.smartificia.projectlms.utils.isConnected
import kotlinx.android.synthetic.main.leads_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeadsFragment : Fragment() {

    companion object {
        fun newInstance() = LeadsFragment()
    }

    private lateinit var viewModel: LeadsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.leads_fragment, container, false)
    }

    private lateinit var leadsAdapter: LeadsAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LeadsViewModel::class.java)
        // TODO: Use the ViewModel

        swipeRefreshLayout.setOnRefreshListener {
            refresh()
            swipeRefreshLayout.isRefreshing = false
        }
        leadsAdapter = LeadsAdapter()

        rvLeads.adapter = leadsAdapter
        rvLeads.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        refresh(isFirst = true)

    }

    private fun refresh(isFirst: Boolean = false) {

        val isCacheEmpty = GlobalLocalCache.getList()?.isEmpty() ?: true

        if (isFirst) progressLoader.visibility = View.VISIBLE
        if (!isConnected(context)) {
            progressLoader.visibility = View.GONE
            if (isCacheEmpty)
                toShowErrorView(true)
            else
                Toast.makeText(context, "You're offline", Toast.LENGTH_LONG).show()
        } else {
            val leadsApi = ApiService.getInstance().getLeadsApi()
            leadsApi.getLeads(GlobalLocalCache.getAccountInfo().userid)
                .enqueue(object : Callback<GenericResponse<LeadInfo>> {
                    override fun onFailure(call: Call<GenericResponse<LeadInfo>>, t: Throwable) {
                        progressLoader.visibility = View.GONE
                        if (isCacheEmpty)
                            toShowErrorView(true)
                        else
                            Toast.makeText(context, "Network Failure", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<GenericResponse<LeadInfo>>,
                        response: Response<GenericResponse<LeadInfo>>
                    ) {
                        progressLoader.visibility = View.GONE
                        val leadResponse = response.body()
                        if (leadResponse != null) {
                            val leadList = leadResponse.response
                            val convertedLeadList = convertToDatedList(leadList)
                            GlobalLocalCache.updateList(leadList)
                            leadsAdapter.submitList(convertedLeadList)
                            toShowErrorView(false)
                        } else {
                            toShowErrorView(true)
                        }
                    }

                })
        }
    }


    private fun toShowErrorView(status: Boolean) {
        if (status) {
            rvLeads.visibility = View.GONE
            errorView.visibility = View.VISIBLE
        } else {
            rvLeads.visibility = View.VISIBLE
            errorView.visibility = View.GONE
        }
    }


}
