package com.smartificia.projectlms.utils

import com.smartificia.projectlms.models.AssignedHeader
import com.smartificia.projectlms.models.DateHeader
import com.smartificia.projectlms.models.LeadInfo
import com.smartificia.projectlms.models.ListItem
import kotlin.random.Random


/**
 * Utility function to convert the list of LEAD Details as per dates assigned
 *
 * Converts the simple list of Leads to list of Leads GROUPED by Date parameter
 */

fun convertToDatedList(leads: List<LeadInfo>): List<ListItem> {
    val treeMap = mutableMapOf<String, MutableList<LeadInfo>>()
    for (lead in leads) {
        var datedList = treeMap[lead.lead_create_on]
        if (datedList == null) {
            datedList = mutableListOf()
        }
        datedList.add(lead)
        treeMap[lead.lead_create_on] = datedList
    }

    val convertedDatedList = mutableListOf<ListItem>()
    for (date in treeMap.keys) {
        convertedDatedList.add(DateHeader(date.substring(0, 10)))
        val thisDateLeads = treeMap[date]
        thisDateLeads?.forEach {
            convertedDatedList.add(it)
        }
    }
    return convertedDatedList
}


/**
 * Utility function to convert the list of LEAD Details as per dates and leads assigned
 *
 * Converts the simple list of Leads to list of Leads GROUPED by Date and Assigned-To parameters
 */

fun convertToDatedAssignedList(leads: List<LeadInfo>): List<ListItem> {
    val treeMap = mutableMapOf<String, MutableMap<String, MutableList<LeadInfo>>>()
    for (lead in leads) {
        if (lead.assigned != null) {
            var datedMap = treeMap[lead.lead_create_on]
            if (datedMap == null)
                datedMap = mutableMapOf()

            var list = datedMap[lead.assigned]
            if (list == null)
                list = mutableListOf()
            list.add(lead)
            datedMap[lead.assigned] = list
            treeMap[lead.lead_create_on] = datedMap
        }
    }

    val convertedList = mutableListOf<ListItem>()

    for (date in treeMap.keys) {
        convertedList.add(DateHeader(date.substring(0, 10)))

        val datedMap = treeMap[date]
        datedMap?.forEach {
            convertedList.add(AssignedHeader(it.key))

            val leadsList = it.value
            leadsList.forEach {
                convertedList.add(it)
            }
        }
    }

    return convertedList
}
