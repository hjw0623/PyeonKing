package com.hjw0623.core.core_domain.repository

import com.hjw0623.core.core_domain.model.response.BaseResponse
import com.hjw0623.core.core_domain.model.response.Item
import com.hjw0623.core.core_domain.model.response.ReviewPage
import com.hjw0623.core.core_domain.model.response.ReviewSummaryResponse
import com.hjw0623.core.core_domain.model.response.SearchItemResponse
import com.hjw0623.core.core_domain.model.network.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getRecommendList(): Flow<DataResourceResult<BaseResponse<List<Item>>>>

    suspend fun searchItems(name: String): Flow<DataResourceResult<BaseResponse<SearchItemResponse>>>

    suspend fun getReviewByItemId(itemId: Long, page: Int): Flow<DataResourceResult<BaseResponse<ReviewPage>>>

    suspend fun getReviewSummaryByItemId(promotionId: Long): Flow<DataResourceResult<BaseResponse<ReviewSummaryResponse>>>
}