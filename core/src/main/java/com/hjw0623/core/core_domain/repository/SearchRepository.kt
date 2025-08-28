package com.hjw0623.core.core_domain.repository

import com.hjw0623.core.core_domain.model.response.BaseResponse
import com.hjw0623.core.core_domain.model.response.SearchItemResponse
import com.hjw0623.core.core_domain.model.network.DataResourceResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface SearchRepository {
    suspend fun getAllItems(): Flow<DataResourceResult<BaseResponse<SearchItemResponse>>>

    suspend fun searchItemsByImg(image: File): Flow<DataResourceResult<BaseResponse<SearchItemResponse>>>

    suspend fun searchItems(name: String): Flow<DataResourceResult<BaseResponse<SearchItemResponse>>>
}