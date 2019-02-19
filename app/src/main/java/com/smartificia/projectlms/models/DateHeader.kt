package com.smartificia.projectlms.models

data class DateHeader(
    val date: String
) : ListItem() {
    override fun id() = "date"

    override fun getType() = TYPE_DATE
}