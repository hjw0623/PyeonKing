package com.hjw0623.core.domain.repository

import com.hjw0623.core.core_network.response.BaseResponse
import com.hjw0623.core.core_network.response.ReviewPage
import com.hjw0623.core.core_network.request.ReviewPostBody
import com.hjw0623.core.core_network.response.ReviewResponse
import com.hjw0623.core.core_network.request.UpdateReviewBody
import com.hjw0623.core.core_network.network.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {

    suspend fun updateReview(updateReviewBody: UpdateReviewBody): Flow<DataResourceResult<BaseResponse<ReviewResponse>>>

    suspend fun getReviewByUserId(page: Int): Flow<DataResourceResult<BaseResponse<ReviewPage>>>

    suspend fun postReview(reviewPostBody: ReviewPostBody): Flow<DataResourceResult<BaseResponse<ReviewResponse>>>
}