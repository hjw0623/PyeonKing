package com.hjw0623.data.repository

import com.hjw0623.core.network.common.DataResourceResult
import com.hjw0623.core.network.request.AuthRequest
import com.hjw0623.core.network.response.AuthResponse
import com.hjw0623.core.network.response.BaseResponse
import com.hjw0623.core.domain.repository.AuthRepository
import com.hjw0623.data.model.mapper.toDomain
import com.hjw0623.data.model.mapper.toDto
import com.hjw0623.data.service.PyeonKingApiService
import com.hjw0623.data.util.safeApiFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: PyeonKingApiService
) : AuthRepository {

    override suspend fun register(authRequest: AuthRequest): Flow<DataResourceResult<BaseResponse<AuthResponse>>> {
        val dto = authRequest.toDto()

        return safeApiFlow {
            val response = apiService.register(dto)

            if (!response.isSuccessful || response.body() == null) {
                throw retrofit2.HttpException(response)
            }

            val body = response.body()!!

            BaseResponse(
                data = body.data.toDomain(),
                message = body.message
            )
        }
    }

    override suspend fun login(authRequest: AuthRequest): Flow<DataResourceResult<BaseResponse<AuthResponse>>> {
        val dto = authRequest.toDto()

        return safeApiFlow {
            val response = apiService.login(dto)

            if (!response.isSuccessful || response.body() == null) {
                throw retrofit2.HttpException(response)
            }

            val body = response.body()!!

            BaseResponse(
                data = body.data.toDomain(),
                message = body.message
            )
        }
    }

    override suspend fun checkNickname(nickname: String): Flow<DataResourceResult<BaseResponse<Boolean>>> {
        return safeApiFlow {
            val response = apiService.checkNickname(nickname)

            if (!response.isSuccessful || response.body() == null) {
                throw retrofit2.HttpException(response)
            }

            val body = response.body()!!

            BaseResponse(
                data = body.data,
                message = body.message
            )
        }
    }
}