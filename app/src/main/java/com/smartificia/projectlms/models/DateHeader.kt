package com.smartificia.projectlms.models


/**
 * Date header class : For distinguishing it from other list items (LEADS, ASSIGNED)
 */
data class DateHeader(
    val date: String
) : ListItem() {
    override fun id() = "date"

    override fun getType() = TYPE_DATE
}