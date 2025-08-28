package com.hjw0623.core.core_domain.repository

import kotlinx.coroutines.flow.Flow

interface UserDataStoreRepository {
    suspend fun saveUserInfo(
        nickname: String,
        email: String,
        accessToken: String,
        refreshToken: String
    )
    suspend fun clearLoginInfo()

    suspend fun saveSearchHistory(query: String)

    suspend fun removeSearchHistory(query: String)

    val nicknameFlow: Flow<String?>
    val emailFlow: Flow<String?>
    val passwordFlow: Flow<String?>
    val accessTokenFlow: Flow<String?>
    val refreshTokenFlow: Flow<String?>
    val isLoggedInFlow: Flow<Boolean>
    val searchHistoryFlow: Flow<List<String>>
}