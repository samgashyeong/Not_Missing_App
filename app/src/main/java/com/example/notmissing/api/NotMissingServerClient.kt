package com.example.notmissing.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NotMissingServerClient {
    private const val baseUrl = "https://not-missing-api.herokuapp.com/api/"

    fun getInstance() : Retrofit {

        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()

        val instance : Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory.create())
            .client(client)
            .build()

        return instance
    }

    fun getApiService() : NotMissingApiService = getInstance().create(NotMissingApiService::class.java)
}