package com.anya.dogipedia.data.model.api.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "message") val message: String?,
    @Json(name = "status") val status: String,
) : Parcelable
