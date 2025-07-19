package com.hjw0623.core.domain.review

import com.hjw0623.core.data.model.BaseResponse
import com.hjw0623.core.data.model.ReviewPage
import com.hjw0623.core.data.model.ReviewPostBody
import com.hjw0623.core.data.model.ReviewResponse
import com.hjw0623.core.data.model.UpdateReviewBody
import com.hjw0623.core.network.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {

    suspend fun updateReview(updateReviewBody: UpdateReviewBody): Flow<DataResourceResult<BaseResponse<ReviewResponse>>>

    suspend fun getReviewByUserId(page: Int): Flow<DataResourceResult<BaseResponse<ReviewPage>>>

    suspend fun postReview(reviewPostBody: ReviewPostBody): Flow<DataResourceResult<BaseResponse<ReviewResponse>>>
}