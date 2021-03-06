package com.anya.dogipedia.data.model.api.error

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Error(
    @Json(name = "status") val status: String,
    @Json(name = "message") val message: String?,
    @Json(name = "code") val code: Int?,
) : Parcelable