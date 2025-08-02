package com.hjw0623.data.repository

import com.hjw0623.core.business_logic.model.response.BaseResponse
import com.hjw0623.core.business_logic.model.response.ReviewPage
import com.hjw0623.core.business_logic.model.request.ReviewPostBody
import com.hjw0623.core.business_logic.model.response.ReviewResponse
import com.hjw0623.core.business_logic.model.request.UpdateReviewBody
import com.hjw0623.core.business_logic.repository.ReviewRepository
import com.hjw0623.core.business_logic.model.network.DataResourceResult
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