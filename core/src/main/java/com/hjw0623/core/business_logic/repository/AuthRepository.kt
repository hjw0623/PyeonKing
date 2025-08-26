package com.hjw0623.core.business_logic.repository

import com.hjw0623.core.business_logic.model.request.AuthRequest
import com.hjw0623.core.business_logic.model.response.AuthResponse
import com.hjw0623.core.business_logic.model.response.BaseResponse
import com.hjw0623.core.business_logic.model.network.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun register(authRequest: AuthRequest): Flow<DataResourceResult<BaseResponse<AuthResponse>>>

    suspend fun login(authRequest: AuthRequest): Flow<DataResourceResult<BaseResponse<AuthResponse>>>

    suspend fun checkNickname(nickname: String): Flow<DataResourceResult<BaseResponse<Boolean>>>
}