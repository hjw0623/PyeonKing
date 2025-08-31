package com.hjw0623.core.domain.repository

import com.hjw0623.core.network.common.BaseResponse
import com.hjw0623.core.network.common.DataResourceResult
import com.hjw0623.core.network.response.search.Item
import com.hjw0623.core.network.response.review.ReviewPage
import com.hjw0623.core.network.response.review.ReviewSummaryResponse
import com.hjw0623.core.network.response.search.SearchItemResponse
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getRecommendList(): Flow<DataResourceResult<BaseResponse<List<Item>>>>

    suspend fun searchItems(name: String): Flow<DataResourceResult<BaseResponse<SearchItemResponse>>>

    suspend fun getReviewByItemId(itemId: Long, page: Int): Flow<DataResourceResult<BaseResponse<ReviewPage>>>

    suspend fun getReviewSummaryByItemId(promotionId: Long): Flow<DataResourceResult<BaseResponse<ReviewSummaryResponse>>>
}