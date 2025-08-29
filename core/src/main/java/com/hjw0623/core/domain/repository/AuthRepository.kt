package com.hjw0623.core.domain.repository

import com.hjw0623.core.network.request.AuthRequest
import com.hjw0623.core.network.response.AuthResponse
import com.hjw0623.core.network.response.BaseResponse
import com.hjw0623.core.network.network.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun register(authRequest: AuthRequest): Flow<DataResourceResult<BaseResponse<AuthResponse>>>

    suspend fun login(authRequest: AuthRequest): Flow<DataResourceResult<BaseResponse<AuthResponse>>>

    suspend fun checkNickname(nickname: String): Flow<DataResourceResult<BaseResponse<Boolean>>>
}