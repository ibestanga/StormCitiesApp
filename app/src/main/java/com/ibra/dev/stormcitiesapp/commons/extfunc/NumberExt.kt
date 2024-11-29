package com.ibra.dev.stormcitiesapp.commons.extfunc

fun Int?.orZero(): Int {
    return this ?: 0
}