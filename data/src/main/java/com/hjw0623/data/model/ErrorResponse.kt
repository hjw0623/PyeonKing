package com.hjw0623.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @field:Json(name = "errorMessage") val errorMessage: String
)

fun parseErrorMessage(body: ResponseBody?): String? {
    return try {
        val json = body?.string()
        val moshi = Moshi.Builder()
            .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(ErrorResponse::class.java)
        val parsed = adapter.fromJson(json ?: "")
        parsed?.errorMessage
    } catch (e: Exception) {
        null
    }
}