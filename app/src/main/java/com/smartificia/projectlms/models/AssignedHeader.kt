package com.smartificia.projectlms.models


/**
 * Assigned header class : For distinguishing it from other list items (LEADS, DATE)
 */

data class AssignedHeader(
    val assignedLabel:String
):ListItem(){
    override fun id() = "Assigned"

    override fun getType() = TYPE_ASSIGNED
}