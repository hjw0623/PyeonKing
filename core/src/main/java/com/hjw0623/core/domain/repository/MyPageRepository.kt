package com.hjw0623.core.domain.repository

import com.hjw0623.core.network.response.BaseResponse
import com.hjw0623.core.network.request.ChangePasswordRequest
import com.hjw0623.core.network.response.ChangePasswordResponse
import com.hjw0623.core.network.network.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface MyPageRepository {

    suspend fun checkNickname(nickname: String): Flow<DataResourceResult<BaseResponse<Boolean>>>

    suspend fun changeNickname(nickname: String): Flow<DataResourceResult<BaseResponse<Boolean>>>

    suspend fun changePassword(
        changePasswordRequest: ChangePasswordRequest
    ): Flow<DataResourceResult<BaseResponse<ChangePasswordResponse>>>
}