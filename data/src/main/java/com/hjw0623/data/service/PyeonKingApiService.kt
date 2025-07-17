package com.hjw0623.data.service

import com.hjw0623.data.model.AuthRequestDto
import com.hjw0623.data.model.AuthResponseDto
import com.hjw0623.data.model.BaseResponseDto
import com.hjw0623.data.model.ChangeNicknameRequestDto
import com.hjw0623.data.model.ChangePasswordRequestDto
import com.hjw0623.data.model.ChangePasswordResponseDto
import com.hjw0623.data.model.ItemDto
import com.hjw0623.data.model.ReviewPageDto
import com.hjw0623.data.model.SearchItemResponseDto
import com.hjw0623.data.model.ReviewPostBodyDto
import com.hjw0623.data.model.ReviewResponseDto
import com.hjw0623.data.model.UpdateReviewBodyDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PyeonKingApiService {

    //회원가입
    @POST("/member")
    suspend fun register(
        @Body request: AuthRequestDto
    ): Response<BaseResponseDto<AuthResponseDto>>

    //로그인
    @POST("/member/login")
    suspend fun login(
        @Body request: AuthRequestDto
    ): Response<BaseResponseDto<AuthResponseDto>>

    //닉네임 중복확인
    @GET("member/check-nickname")
    suspend fun checkNickname(
        @Query("nickname") nickname: String
    ): Response<BaseResponseDto<Boolean>>

    //비밀번호 변경
    @PATCH("/member/password")
    suspend fun changePassword(
        @Body updateRequest: ChangePasswordRequestDto
    ): Response<BaseResponseDto<ChangePasswordResponseDto>>

    //닉네임 변경
    @Headers("Authorization: required")
    @PATCH("/member/nickname")
    suspend fun changeNickname(
        @Body updateRequest: ChangeNicknameRequestDto
    ): Response<BaseResponseDto<Boolean>>

    //전체 상품 조회
    @GET("konbini/items")
    suspend fun getAllItems(
        @Query("name") name: String = "",
        @Query("strength") strength: String = "STRONG"
    ): Response<BaseResponseDto<SearchItemResponseDto>>

    //상품 검색
    @GET("konbini/items")
    suspend fun searchItems(
        @Query("name") name: String,
        @Query("strength") strength: String = "STRONG"
    ): Response<BaseResponseDto<SearchItemResponseDto>>

    //이미지 상품 검색
    @Multipart
    @POST("konbini/items/image")
    suspend fun searchItemsByImg(
        @Part image: MultipartBody.Part
    ): Response<BaseResponseDto<SearchItemResponseDto>>

    //상품별 리뷰 조회
    @Headers("Authorization: required")
    @GET("/comment/list/{itemID}/{page}")
    suspend fun getReviewByItemId(
        @Path("itemID") itemID: Int,
        @Path("page") page: Int,
    ): Response<BaseResponseDto<ReviewPageDto>>

    //유저별 리뷰 조회
    @Headers("Authorization: required")
    @GET("/comment/list/{page}")
    suspend fun getReviewByUserId(
        @Path("page") page: Int,
    ): Response<BaseResponseDto<ReviewPageDto>>

    //리뷰 작성하기
    @Headers("Authorization: required")
    @POST("/comment")
    suspend fun postReview(
        @Body reviewPostBody: ReviewPostBodyDto,
    ): Response<BaseResponseDto<ReviewResponseDto>>

    //리뷰 수정하기
    @Headers("Authorization: required")
    @PUT("/comment")
    suspend fun updateReview(
        @Body updateReviewBodyDto: UpdateReviewBodyDto,
        @Header("Authorization") accessToken: String
    ): Response<BaseResponseDto<ReviewResponseDto>>

    //추천상품 조회
    @Headers("Authorization: required")
    @GET("/member/promotions/recommend")
    suspend fun getRecommendList(): Response<BaseResponseDto<List<ItemDto>>>
}
