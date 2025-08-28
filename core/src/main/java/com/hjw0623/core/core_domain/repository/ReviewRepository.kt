package com.hjw0623.core.core_domain.repository

import com.hjw0623.core.core_domain.model.response.BaseResponse
import com.hjw0623.core.core_domain.model.response.ReviewPage
import com.hjw0623.core.core_domain.model.request.ReviewPostBody
import com.hjw0623.core.core_domain.model.response.ReviewResponse
import com.hjw0623.core.core_domain.model.request.UpdateReviewBody
import com.hjw0623.core.core_domain.model.network.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {

    suspend fun updateReview(updateReviewBody: UpdateReviewBody): Flow<DataResourceResult<BaseResponse<ReviewResponse>>>

    suspend fun getReviewByUserId(page: Int): Flow<DataResourceResult<BaseResponse<ReviewPage>>>

    suspend fun postReview(reviewPostBody: ReviewPostBody): Flow<DataResourceResult<BaseResponse<ReviewResponse>>>
}