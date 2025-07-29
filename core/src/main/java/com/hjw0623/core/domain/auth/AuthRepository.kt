package com.hjw0623.core.domain.auth

import com.hjw0623.core.domain.model.AuthRequest
import com.hjw0623.core.domain.model.AuthResponse
import com.hjw0623.core.domain.model.BaseResponse
import com.hjw0623.core.network.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun register(authRequest: AuthRequest): Flow<DataResourceResult<BaseResponse<AuthResponse>>>

    suspend fun login(authRequest: AuthRequest): Flow<DataResourceResult<BaseResponse<AuthResponse>>>

    suspend fun checkNickname(nickname: String): Flow<DataResourceResult<BaseResponse<Boolean>>>

}