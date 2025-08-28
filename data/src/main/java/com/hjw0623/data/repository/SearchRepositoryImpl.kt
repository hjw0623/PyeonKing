package com.hjw0623.data.repository

import com.hjw0623.core.core_domain.model.network.DataResourceResult
import com.hjw0623.core.core_domain.model.response.BaseResponse
import com.hjw0623.core.core_domain.model.response.SearchItemResponse
import com.hjw0623.core.core_domain.repository.SearchRepository
import com.hjw0623.data.model.mapper.toDomain
import com.hjw0623.data.service.PyeonKingApiService
import com.hjw0623.data.util.safeApiFlow
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: PyeonKingApiService
) : SearchRepository {

    override suspend fun getAllItems(): Flow<DataResourceResult<BaseResponse<SearchItemResponse>>> {
        return safeApiFlow {
            val response = apiService.getAllItems()

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

    override suspend fun searchItemsByImg(image: File): Flow<DataResourceResult<BaseResponse<SearchItemResponse>>> {
        return safeApiFlow {
            val requestFile = image.asRequestBody("image/*".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData(
                name = "img",
                filename = image.name,
                body = requestFile
            )
            val response = apiService.searchItemsByImg(multipartBody)

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

    override suspend fun searchItems(name: String): Flow<DataResourceResult<BaseResponse<SearchItemResponse>>> {
        return safeApiFlow {
            val response = apiService.searchItems(name)

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