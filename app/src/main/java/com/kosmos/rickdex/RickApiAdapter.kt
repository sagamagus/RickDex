package com.kosmos.rickdex

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object RickApiClient {
    val apiService: RickApiService by lazy {
        RetrofitClient.retrofit.create(RickApiService::class.java)
    }
}