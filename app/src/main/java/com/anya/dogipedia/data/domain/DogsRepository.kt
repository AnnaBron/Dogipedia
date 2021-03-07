package com.anya.dogipedia.data.domain

import com.anya.dogipedia.data.model.api.response.ApiResponse
import com.anya.dogipedia.data.service.ApiService
import retrofit2.Response
import javax.inject.Inject


class DogsRepository
@Inject constructor(
    private val apiService: ApiService,
) : Repositories.DogsRepository {

    override suspend fun getAllBreeds(): Response<ApiResponse> = apiService.getAllBreeds()

    override suspend fun getAllBreedsImages(breed: String): Response<ApiResponse> = apiService.getDogsByBreed(breed)

}