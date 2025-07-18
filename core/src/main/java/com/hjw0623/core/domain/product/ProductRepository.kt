package com.hjw0623.core.domain.product

import com.hjw0623.core.data.model.BaseResponse
import com.hjw0623.core.data.model.Item
import com.hjw0623.core.data.model.SearchItemResponse
import com.hjw0623.core.network.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getRecommendList(): Flow<DataResourceResult<BaseResponse<List<Item>>>>

    suspend fun searchItems(name: String): Flow<DataResourceResult<BaseResponse<SearchItemResponse>>>
}