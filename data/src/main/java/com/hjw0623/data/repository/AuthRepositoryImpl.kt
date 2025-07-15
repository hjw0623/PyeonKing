package com.hjw0623.data.repository

import com.hjw0623.core.data.model.AuthRequest
import com.hjw0623.core.data.model.AuthResponse
import com.hjw0623.core.data.model.BaseResponse
import com.hjw0623.core.domain.auth.AuthRepository
import com.hjw0623.core.network.DataResourceResult
import com.hjw0623.data.model.mapper.toDomain
import com.hjw0623.data.model.mapper.toDto
import com.hjw0623.data.remote.PyeonKingApiClient
import com.hjw0623.data.util.safeApiFlow
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl: AuthRepository {
    private val apiService = PyeonKingApiClient.pyeonKingApiService

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