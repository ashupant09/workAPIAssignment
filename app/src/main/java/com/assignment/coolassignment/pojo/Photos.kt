package com.assignment.coolassignment.pojo

data class Photos(
    var page: Int? = null,
    var pages: Int? = null,
    var perpage: Int? = null,
    var photo: List<Photo>? = null,
    var total: String? = null
)