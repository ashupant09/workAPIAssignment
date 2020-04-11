package com.assignment.coolassignment.network

import com.assignment.coolassignment.pojo.FlickerResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FlickerApi {

    @GET("rest/")
    fun getFlickerData(
        @QueryMap options: Map<String, String>
    ): Observable<Response<FlickerResponse>>
}