package com.smartificia.projectlms.models

abstract class ListItem {

    companion object {
        const val TYPE_DATE=0
        const val  TYPE_LEAD=1
        const val TYPE_ASSIGNED=2
    }
    abstract fun id():String
    abstract fun getType():Int
}