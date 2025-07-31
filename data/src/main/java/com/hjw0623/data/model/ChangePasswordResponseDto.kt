package com.hjw0623.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChangePasswordResponseDto(
    @field: Json(name = "result") val result: Boolean
)
