package com.anya.dogipedia.ui.dogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.anya.dogipedia.data.domain.Repositories
import com.anya.dogipedia.data.model.api.response.ApiResponse
import com.anya.dogipedia.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DogsViewModel
@Inject constructor(
    private val dogsRepository: Repositories.DogsRepository,
) : ViewModel() {

    fun getDogsImages(breed: String, subBreed: String?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            var result: Response<ApiResponse>? = null

            if(subBreed != null){
                result = dogsRepository.getAllSubBreedsImages(breed, subBreed)
            } else {
                result = dogsRepository.getAllBreedsImages(breed)
            }

            if (result.isSuccessful && result.body() != null && result.body()?.status == "success") {
                emit(Resource.success(data = result.body()!!.message))
            } else {
                emit(
                    Resource.error(
                        data = null,
                        message = result.errorBody()?.string() ?: "Error Occurred!"
                    )
                )
            }

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}