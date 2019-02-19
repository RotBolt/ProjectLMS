package com.smartificia.projectlms.models

data class AssignedHeader(
    val assignedLabel:String
):ListItem(){
    override fun id() = "Assigned"

    override fun getType() = TYPE_ASSIGNED
}