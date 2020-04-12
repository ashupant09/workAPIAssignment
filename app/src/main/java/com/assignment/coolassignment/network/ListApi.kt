package com.assignment.coolassignment.network

import com.assignment.coolassignment.pojo.Data
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface ListApi {
    @GET("posts")
    suspend fun getData(): Response<List<Data>>
    //fun getData(): Observable<Response<List<Data>>>
}