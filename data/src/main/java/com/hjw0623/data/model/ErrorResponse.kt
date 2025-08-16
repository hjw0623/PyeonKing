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

fun httpErrorMessage(code: Int): String = when (code) {
    400 -> "잘못된 요청입니다."
    401 -> "로그인이 필요합니다."
    403 -> "접근 권한이 없습니다."
    404 -> "요청한 리소스를 찾을 수 없습니다."
    409 -> "이미 존재하거나 충돌이 발생했습니다."
    422 -> "입력 값 검증에 실패했습니다."
    429 -> "요청이 너무 많습니다. 잠시 후 다시 시도해주세요."
    in 500..599 -> "서버 오류가 발생했습니다."
    else -> "요청 처리 중 오류가 발생했습니다."
}