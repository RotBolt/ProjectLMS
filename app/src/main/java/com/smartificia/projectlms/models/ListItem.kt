package com.smartificia.projectlms.models


/**
 *  abstract class that every list item should extend to define
 *  the type of List Item (DATE, ASSIGNED OR LEAD)
 */

abstract class ListItem {

    companion object {
        const val TYPE_DATE=0
        const val  TYPE_LEAD=1
        const val TYPE_ASSIGNED=2
    }
    abstract fun id():String
    abstract fun getType():Int
}