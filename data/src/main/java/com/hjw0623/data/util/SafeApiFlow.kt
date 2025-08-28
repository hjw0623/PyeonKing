package com.hjw0623.data.util

import com.hjw0623.core.core_domain.model.network.DataResourceResult
import com.hjw0623.data.model.httpErrorMessage
import com.hjw0623.data.model.parseErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import kotlin.coroutines.cancellation.CancellationException

fun <T> safeApiFlow(apiCall: suspend () -> T): Flow<DataResourceResult<T>> = flow {
    emit(DataResourceResult.Loading)

    try {
        emit(DataResourceResult.Success(apiCall()))
    } catch (e: Throwable) {
        if (e is CancellationException) throw e

        val userMessage = when (e) {
            is HttpException -> {
                val code = e.code()
                val parsed = parseErrorMessage(e.response()?.errorBody())

                if (code in 500..599) {
                    httpErrorMessage(code)
                } else {
                    if (code == 400 && parsed == "엑세스 토큰이 잘못되었습니다.") {
                        "리뷰 확인을 위해 로그인이 필요합니다."
                    } else {
                        parsed ?: httpErrorMessage(code)
                    }
                }
            }

            is IOException -> "인터넷 연결을 확인해주세요."
            else -> e.message ?: "알 수 없는 오류가 발생했습니다."
        }

        emit(DataResourceResult.Failure(Exception(userMessage)))
    }
}