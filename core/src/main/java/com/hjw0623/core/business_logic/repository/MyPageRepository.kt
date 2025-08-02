package com.hjw0623.core.business_logic.repository

import com.hjw0623.core.business_logic.model.response.BaseResponse
import com.hjw0623.core.business_logic.model.request.ChangePasswordRequest
import com.hjw0623.core.business_logic.model.response.ChangePasswordResponse
import com.hjw0623.core.business_logic.model.network.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface MyPageRepository {

    suspend fun checkNickname(nickname: String): Flow<DataResourceResult<BaseResponse<Boolean>>>

    suspend fun changeNickname(nickname: String): Flow<DataResourceResult<BaseResponse<Boolean>>>

    suspend fun changePassword(
        changePasswordRequest: ChangePasswordRequest
    ): Flow<DataResourceResult<BaseResponse<ChangePasswordResponse>>>
}