package com.hjw0623.core.domain.review

import com.hjw0623.core.domain.model.BaseResponse
import com.hjw0623.core.domain.model.ReviewPage
import com.hjw0623.core.domain.model.ReviewPostBody
import com.hjw0623.core.domain.model.ReviewResponse
import com.hjw0623.core.domain.model.UpdateReviewBody
import com.hjw0623.core.network.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {

    suspend fun updateReview(updateReviewBody: UpdateReviewBody): Flow<DataResourceResult<BaseResponse<ReviewResponse>>>

    suspend fun getReviewByUserId(page: Int): Flow<DataResourceResult<BaseResponse<ReviewPage>>>

    suspend fun postReview(reviewPostBody: ReviewPostBody): Flow<DataResourceResult<BaseResponse<ReviewResponse>>>
}