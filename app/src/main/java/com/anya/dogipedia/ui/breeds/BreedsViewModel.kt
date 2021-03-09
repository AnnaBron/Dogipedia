package com.anya.dogipedia.ui.breeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.anya.dogipedia.data.domain.Repositories
import com.anya.dogipedia.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class BreedsViewModel
@Inject constructor(
    private val dogsRepository: Repositories.DogsRepository,
) : ViewModel() {

    fun getDogs() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val result = dogsRepository.getAllBreeds()
            if (result.isSuccessful && result.body() != null && result.body()?.status == "success") {
                emit(Resource.success(data = result.body()!!.message))
            } else {
                emit(Resource.error(data = null, message = result.errorBody()?.string() ?: "Error Occurred!"))
            }
        } catch (exception: IOException) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}