package com.anya.dogipedia.data.domain

import com.anya.dogipedia.data.model.api.response.ApiResponse
import retrofit2.Response

interface Repositories {
    interface DogsRepository {
        suspend fun getAllBreeds(): Response<ApiResponse>
        suspend fun getAllBreedsImages(breed : String): Response<ApiResponse>
    }
}