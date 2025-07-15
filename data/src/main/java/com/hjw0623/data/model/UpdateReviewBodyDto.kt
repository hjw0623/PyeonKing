package com.hjw0623.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateReviewBodyDto(
    @field:Json(name = "commentId") val commentId: Long,
    @field:Json(name = "star") val star: Int,
    @field:Json(name = "content") val content: String
)