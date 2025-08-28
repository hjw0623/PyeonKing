package com.hjw0623.data.repository

import com.hjw0623.core.core_network.network.DataResourceResult
import com.hjw0623.core.core_domain.model.request.ChangeNicknameRequest
import com.hjw0623.core.core_domain.model.request.ChangePasswordRequest
import com.hjw0623.core.core_domain.model.response.BaseResponse
import com.hjw0623.core.core_domain.model.response.ChangePasswordResponse
import com.hjw0623.core.core_domain.repository.MyPageRepository
import com.hjw0623.data.model.mapper.toDomain
import com.hjw0623.data.model.mapper.toDto
import com.hjw0623.data.service.PyeonKingApiService
import com.hjw0623.data.util.safeApiFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val apiService: PyeonKingApiService
) : MyPageRepository {

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