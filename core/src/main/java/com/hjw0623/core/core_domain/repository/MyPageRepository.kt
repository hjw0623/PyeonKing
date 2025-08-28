package com.hjw0623.core.core_domain.repository

import com.hjw0623.core.core_domain.model.response.BaseResponse
import com.hjw0623.core.core_domain.model.request.ChangePasswordRequest
import com.hjw0623.core.core_domain.model.response.ChangePasswordResponse
import com.hjw0623.core.core_network.network.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface MyPageRepository {

    suspend fun checkNickname(nickname: String): Flow<DataResourceResult<BaseResponse<Boolean>>>

    suspend fun changeNickname(nickname: String): Flow<DataResourceResult<BaseResponse<Boolean>>>

    suspend fun changePassword(
        changePasswordRequest: ChangePasswordRequest
    ): Flow<DataResourceResult<BaseResponse<ChangePasswordResponse>>>
}