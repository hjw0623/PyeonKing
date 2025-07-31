package com.hjw0623.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChangeNicknameRequestDto(
    @field:Json(name = "nickname") val nickname: String
)
