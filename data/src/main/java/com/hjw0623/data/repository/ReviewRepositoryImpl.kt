package com.hjw0623.data.repository

import com.hjw0623.core.data.model.BaseResponse
import com.hjw0623.core.data.model.ReviewPage
import com.hjw0623.core.data.model.ReviewPostBody
import com.hjw0623.core.data.model.ReviewResponse
import com.hjw0623.core.data.model.UpdateReviewBody
import com.hjw0623.core.domain.review.ReviewRepository
import com.hjw0623.core.network.DataResourceResult
import com.hjw0623.data.model.mapper.toDomain
import com.hjw0623.data.model.mapper.toDto
import com.hjw0623.data.remote.PyeonKingApiClient
import com.hjw0623.data.util.safeApiFlow
import kotlinx.coroutines.flow.Flow

class ReviewRepositoryImpl: ReviewRepository {
    private val apiService = PyeonKingApiClient.pyeonKingApiService
    override suspend fun updateReview(updateReviewBody: UpdateReviewBody): Flow<DataResourceResult<BaseResponse<ReviewResponse>>> {
        return safeApiFlow {
            val dto = updateReviewBody.toDto()
            val response = apiService.updateReview(dto)

            if (!response.isSuccessful || response.body() == null) {
                throw retrofit2.HttpException(response)
            }

            val body = response.body()!!

            BaseResponse(
                data = body.data.toDomain(),
                message = body.message
            )
        }
    }

    override suspend fun getReviewByUserId(page: Int): Flow<DataResourceResult<BaseResponse<ReviewPage>>> {
        return safeApiFlow {
            val response = apiService.getReviewByUserId(page)

            if (!response.isSuccessful || response.body() == null) {
                throw retrofit2.HttpException(response)
            }

            val body = response.body()!!

            BaseResponse(
                data = body.data.toDomain(),
                message = body.message
            )
        }
    }

    override suspend fun postReview(reviewPostBody: ReviewPostBody): Flow<DataResourceResult<BaseResponse<ReviewResponse>>> {
        return safeApiFlow {
            val dto = reviewPostBody.toDto()
            val response = apiService.postReview(dto)

            if (!response.isSuccessful || response.body() == null) {
                throw retrofit2.HttpException(response)
            }

            val body = response.body()!!

            BaseResponse(
                data = body.data.toDomain(),
                message = body.message
            )
        }
    }
}