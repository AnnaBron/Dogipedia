package com.anya.dogipedia.data.service

import com.anya.dogipedia.data.model.api.response.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("breeds/list/all")
    suspend fun getAllBreeds() : Response<ApiResponse>

    @GET("breed/{breed}/images")
    suspend fun getDogsByBreed(@Path("breed") breed: String): Response<ApiResponse>

    @GET("breed/{breed}/images/random")
    suspend fun getRandomDogByBreed(@Path("breed") breed: String): Response<ApiResponse>

    @GET("breed/{breed}/{subBreed}/images")
    suspend fun getDogsBySubBreed(@Path("breed") breed: String, @Path("subBreed") subBreed: String): Response<ApiResponse>



}
