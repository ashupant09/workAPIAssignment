package com.assignment.coolassignment.pojo

import com.assignment.coolassignment.network.AppResponse

data class FlickerResponse(
    var photos: Photos? = null,
    var stat: String? = null
): AppResponse