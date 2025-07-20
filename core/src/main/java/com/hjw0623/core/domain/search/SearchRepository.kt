package com.hjw0623.core.domain.search

import com.hjw0623.core.data.model.BaseResponse
import com.hjw0623.core.data.model.SearchItemResponse
import com.hjw0623.core.network.DataResourceResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface SearchRepository {
    suspend fun getAllItems(): Flow<DataResourceResult<BaseResponse<SearchItemResponse>>>

    suspend fun searchItemsByImg(image: File): Flow<DataResourceResult<BaseResponse<SearchItemResponse>>>

    suspend fun searchItems(name: String): Flow<DataResourceResult<BaseResponse<SearchItemResponse>>>
}