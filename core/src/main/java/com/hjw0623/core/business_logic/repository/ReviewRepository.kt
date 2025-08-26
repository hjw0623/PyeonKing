package com.hjw0623.core.business_logic.repository

import com.hjw0623.core.business_logic.model.response.BaseResponse
import com.hjw0623.core.business_logic.model.response.ReviewPage
import com.hjw0623.core.business_logic.model.request.ReviewPostBody
import com.hjw0623.core.business_logic.model.response.ReviewResponse
import com.hjw0623.core.business_logic.model.request.UpdateReviewBody
import com.hjw0623.core.business_logic.model.network.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {

    suspend fun updateReview(updateReviewBody: UpdateReviewBody): Flow<DataResourceResult<BaseResponse<ReviewResponse>>>

    suspend fun getReviewByUserId(page: Int): Flow<DataResourceResult<BaseResponse<ReviewPage>>>

    suspend fun postReview(reviewPostBody: ReviewPostBody): Flow<DataResourceResult<BaseResponse<ReviewResponse>>>
}