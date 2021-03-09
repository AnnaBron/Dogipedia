package com.anya.dogipedia.data.model.api.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "message") var message: @RawValue Any? = null,
    @Json(name = "status") val status: String
) : Parcelable
