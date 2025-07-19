package com.hjw0623.data.repository

import com.hjw0623.core.data.model.BaseResponse
import com.hjw0623.core.data.model.Item
import com.hjw0623.core.data.model.ReviewPage
import com.hjw0623.core.data.model.ReviewSummaryResponse
import com.hjw0623.core.data.model.SearchItemResponse
import com.hjw0623.core.domain.product.ProductRepository
import com.hjw0623.core.network.DataResourceResult
import com.hjw0623.data.model.mapper.toDomain
import com.hjw0623.data.remote.PyeonKingApiClient
import com.hjw0623.data.util.safeApiFlow
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class ProductRepositoryImpl : ProductRepository {
    private val apiService = PyeonKingApiClient.pyeonKingApiService

    override suspend fun getRecommendList(): Flow<DataResourceResult<BaseResponse<List<Item>>>> {
        return safeApiFlow {
            val response = apiService.getRecommendList()

            if (!response.isSuccessful || response.body() == null) {
                throw HttpException(response)
            }

            val body = response.body()!!

            BaseResponse(
                data = body.data.map { it.toDomain() },
                message = body.message
            )
        }
    }

    override suspend fun searchItems(name: String): Flow<DataResourceResult<BaseResponse<SearchItemResponse>>> {
        return safeApiFlow {
            val response = apiService.searchItems(name)

            if (!response.isSuccessful || response.body() == null) {
                throw HttpException(response)
            }

            val body = response.body()!!

            BaseResponse(
                data = body.data.toDomain(),
                message = body.message
            )
        }
    }

    override suspend fun getReviewByItemId(
        itemId: Long,
        page: Int
    ): Flow<DataResourceResult<BaseResponse<ReviewPage>>> {
        return safeApiFlow {
            val response = apiService.getReviewByItemId(itemId, page)
            if (!response.isSuccessful || response.body() == null) {
                throw HttpException(response)
            }

            val body = response.body()!!
            BaseResponse(
                data = body.data.toDomain(),
                message = body.message
            )
        }
    }

    override suspend fun getReviewSummaryByItemId(promotionId: Long): Flow<DataResourceResult<BaseResponse<ReviewSummaryResponse>>> {
        return safeApiFlow {
            val response = apiService.getReviewSummaryByItemId(promotionId)

            if (!response.isSuccessful || response.body() == null) {
                throw HttpException(response)
            }

            val body = response.body()!!

            BaseResponse(
                data = body.data.toDomain(),
                message = body.message
            )
        }
    }
}