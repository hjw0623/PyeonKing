package com.hjw0623.core.domain.repository

import com.hjw0623.core.network.common.BaseResponse
import com.hjw0623.core.network.common.DataResourceResult
import com.hjw0623.core.network.request.review.ReviewPostBody
import com.hjw0623.core.network.request.review.UpdateReviewBody
import com.hjw0623.core.network.response.review.ReviewPage
import com.hjw0623.core.network.response.review.ReviewResponse
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {

    suspend fun updateReview(updateReviewBody: UpdateReviewBody): Flow<DataResourceResult<BaseResponse<ReviewResponse>>>

    suspend fun getReviewByUserId(page: Int): Flow<DataResourceResult<BaseResponse<ReviewPage>>>

    suspend fun postReview(reviewPostBody: ReviewPostBody): Flow<DataResourceResult<BaseResponse<ReviewResponse>>>
}