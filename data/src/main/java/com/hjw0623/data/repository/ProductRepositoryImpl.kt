package com.hjw0623.data.repository

import com.hjw0623.core.domain.repository.ProductRepository
import com.hjw0623.core.network.common.BaseResponse
import com.hjw0623.core.network.common.DataResourceResult
import com.hjw0623.core.network.response.search.Item
import com.hjw0623.core.network.response.review.ReviewPage
import com.hjw0623.core.network.response.review.ReviewSummaryResponse
import com.hjw0623.core.network.response.search.SearchItemResponse
import com.hjw0623.data.model.mapper.toDomain
import com.hjw0623.data.service.PyeonKingApiService
import com.hjw0623.data.util.safeApiFlow
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: PyeonKingApiService
) : ProductRepository {

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