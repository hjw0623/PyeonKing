package com.hjw0623.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponseDto<T>(
    @field:Json(name = "data") val data: T,
    @field:Json(name = "message") val message: String
)
