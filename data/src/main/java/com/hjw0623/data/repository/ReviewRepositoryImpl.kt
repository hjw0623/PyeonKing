package com.hjw0623.data.repository

import com.hjw0623.core.domain.repository.ReviewRepository
import com.hjw0623.core.network.common.BaseResponse
import com.hjw0623.core.network.common.DataResourceResult
import com.hjw0623.core.network.request.review.ReviewPostBody
import com.hjw0623.core.network.request.review.UpdateReviewBody
import com.hjw0623.core.network.response.review.ReviewPage
import com.hjw0623.core.network.response.review.ReviewResponse
import com.hjw0623.data.model.mapper.toDomain
import com.hjw0623.data.model.mapper.toDto
import com.hjw0623.data.service.PyeonKingApiService
import com.hjw0623.data.util.safeApiFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val apiService: PyeonKingApiService
) : ReviewRepository {

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