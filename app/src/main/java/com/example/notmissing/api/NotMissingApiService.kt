package com.example.notmissing.api

import com.example.notmissing.model.missingpeople.MissingPeopleModel
import com.example.notmissing.model.safespot.SafeSpotModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NotMissingApiService {
    @GET("missingPeople/{pageIndex}")
    fun getMissingPeople(@Path("pageIndex") pageIndex : String): Call<MissingPeopleModel>

    @GET("safeSpot/{pageIndex}")
    fun getSafeSpot(@Path("pageIndex") pageIndex: String): Call<SafeSpotModel>
}