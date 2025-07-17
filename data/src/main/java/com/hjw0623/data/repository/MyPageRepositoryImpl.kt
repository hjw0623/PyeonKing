package com.hjw0623.data.repository

import com.hjw0623.core.data.model.BaseResponse
import com.hjw0623.core.data.model.ChangeNicknameRequest
import com.hjw0623.core.data.model.ChangePasswordRequest
import com.hjw0623.core.data.model.ChangePasswordResponse
import com.hjw0623.core.domain.mypage.MyPageRepository
import com.hjw0623.core.network.DataResourceResult
import com.hjw0623.data.model.mapper.toDomain
import com.hjw0623.data.model.mapper.toDto
import com.hjw0623.data.remote.PyeonKingApiClient
import com.hjw0623.data.util.safeApiFlow
import kotlinx.coroutines.flow.Flow

class MyPageRepositoryImpl: MyPageRepository {
    val apiService = PyeonKingApiClient.pyeonKingApiService

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

    override suspend fun changeNickname(nickname: String): Flow<DataResourceResult<BaseResponse<Boolean>>> {
        return safeApiFlow {
            val dto = ChangeNicknameRequest(nickname).toDto()
            val response = apiService.changeNickname(dto)

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

    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): Flow<DataResourceResult<BaseResponse<ChangePasswordResponse>>> {
        return safeApiFlow {
            val dto = changePasswordRequest.toDto()
            val response = apiService.changePassword(dto)

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
}