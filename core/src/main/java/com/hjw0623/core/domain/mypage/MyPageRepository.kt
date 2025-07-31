package com.hjw0623.core.domain.mypage


import com.hjw0623.core.domain.model.BaseResponse
import com.hjw0623.core.domain.model.ChangePasswordRequest
import com.hjw0623.core.domain.model.ChangePasswordResponse
import com.hjw0623.core.network.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface MyPageRepository {

    suspend fun checkNickname(nickname: String): Flow<DataResourceResult<BaseResponse<Boolean>>>

    suspend fun changeNickname(nickname: String): Flow<DataResourceResult<BaseResponse<Boolean>>>

    suspend fun changePassword(
        changePasswordRequest: ChangePasswordRequest
    ): Flow<DataResourceResult<BaseResponse<ChangePasswordResponse>>>
}