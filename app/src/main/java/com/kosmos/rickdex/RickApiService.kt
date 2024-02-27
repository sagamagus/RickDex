package com.kosmos.rickdex

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RickApiService {
    @GET("character/")
fun getCharsbyPage(@Query("page") postId: Int): Call<RickResponse>
}