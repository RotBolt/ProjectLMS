package com.smartificia.projectlms.ui.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.smartificia.projectlms.R
import com.smartificia.projectlms.models.AssignedHeader
import com.smartificia.projectlms.models.DateHeader
import com.smartificia.projectlms.models.LeadInfo
import com.smartificia.projectlms.models.ListItem
import com.smartificia.projectlms.ui.activities.LeadDetailsActivity
import com.smartificia.projectlms.utils.Labels

class LeadsAdapter : ListAdapter<ListItem, LeadsAdapter.LeadViewHolder>(diffCallBack) {


    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(old: ListItem, new: ListItem) =
                old.id() == new.id() && old.getType() == new.getType()

            override fun areContentsTheSame(old: ListItem, new: ListItem) = old == new
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeadViewHolder {

        return when (viewType) {
            ListItem.TYPE_LEAD -> LeadViewHolder(
                parent.context,
                LayoutInflater.from(parent.context).inflate(R.layout.layout_lead_item, parent, false)
            )
            ListItem.TYPE_DATE -> LeadViewHolder(
                parent.context,
                LayoutInflater.from(parent.context).inflate(R.layout.layout_date_header, parent, false)
            )
            else -> LeadViewHolder(
                parent.context,
                LayoutInflater.from(parent.context).inflate(R.layout.layout_assigned_header, parent, false)
            )
        }

    }

    override fun getItemViewType(position: Int) = getItem(position).getType()


    override fun onBindViewHolder(holder: LeadViewHolder, p1: Int) {
        val type = getItemViewType(p1)
        when (type) {
            ListItem.TYPE_LEAD -> holder.bindUser(getItem(p1) as LeadInfo)
            ListItem.TYPE_DATE -> holder.bindDate(getItem(p1) as DateHeader)
            else -> holder.bindAssignedHeader(getItem(p1) as AssignedHeader)
        }
    }


    class LeadViewHolder(private val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        private val tvNumber = itemView.findViewById<TextView>(R.id.tvNumber)
        private val tvStatus = itemView.findViewById<TextView>(R.id.tvStatus)
        private val tvFollowUp = itemView.findViewById<TextView>(R.id.tvFollowUp)
        private val tvInitials = itemView.findViewById<TextView>(R.id.tvInitials)
        private val tvAssigned = itemView.findViewById<TextView>(R.id.tvAssigned)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        private val tvAssignedHeader = itemView.findViewById<TextView>(R.id.tvAssignedHeader)

        fun bindUser(leadInfo: LeadInfo) {
            tvName.text = leadInfo.leadname
            tvNumber.text = leadInfo.phone
            val initials = with(leadInfo.leadname) {
                val names = split(" ")
                if (names.size > 1)
                    " ${names[0][0]}${names[1][0]} ".toUpperCase()
                else
                    "  ${names[0][0]}  ".toUpperCase()
            }
            tvInitials.text = initials

            tvStatus.text = " ${leadInfo.statusname}"
            tvFollowUp.text = " ${leadInfo.followup_time}"

            itemView.setOnClickListener {
                val intent = Intent(context, LeadDetailsActivity::class.java).apply {
                    putExtra(Labels.leadInfo, leadInfo)
                }
                context.startActivity(intent)
            }

            if (leadInfo.assigned == null) {
                tvAssigned.visibility = View.GONE
            } else {
                tvAssigned.text = " ${leadInfo.assigned}"
            }

        }

        fun bindDate(date: DateHeader) {
            tvDate.text = date.date
        }


        fun bindAssignedHeader(assignedHeader: AssignedHeader) {
            tvAssignedHeader.text = assignedHeader.assignedLabel
        }
    }
}