package com.kamer.aviasalestest.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SearchCityService {

    @GET("autocomplete")
    fun findCities(@Query("term") query: String, @Query("lang") language: String = "ru"): Single<Response>

}