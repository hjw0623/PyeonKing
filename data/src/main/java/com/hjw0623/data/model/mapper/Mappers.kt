package com.hjw0623.data.model.mapper

import com.hjw0623.core.constants.Promotion
import com.hjw0623.core.data.model.AuthRequest
import com.hjw0623.core.data.model.AuthResponse
import com.hjw0623.core.data.model.ChangeNicknameRequest
import com.hjw0623.core.data.model.ChangePasswordRequest
import com.hjw0623.core.data.model.ChangePasswordResponse
import com.hjw0623.core.data.model.Item
import com.hjw0623.core.data.model.ReviewPage
import com.hjw0623.core.data.model.ReviewProduct
import com.hjw0623.core.data.model.ReviewResponse
import com.hjw0623.core.data.model.ReviewSummaryResponse
import com.hjw0623.core.data.model.SearchItemResponse
import com.hjw0623.core.data.model.UpdateReviewBody
import com.hjw0623.data.model.AuthRequestDto
import com.hjw0623.data.model.AuthResponseDto
import com.hjw0623.data.model.ChangeNicknameRequestDto
import com.hjw0623.data.model.ChangePasswordRequestDto
import com.hjw0623.data.model.ChangePasswordResponseDto
import com.hjw0623.data.model.ItemDto
import com.hjw0623.data.model.ReviewPageDto
import com.hjw0623.data.model.ReviewProductDto
import com.hjw0623.data.model.ReviewResponseDto
import com.hjw0623.data.model.ReviewSummaryResponseDto
import com.hjw0623.data.model.SearchItemResponseDto
import com.hjw0623.data.model.UpdateReviewBodyDto


fun AuthResponseDto.toDomain(): AuthResponse {
    return AuthResponse(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}

fun AuthRequest.toDto(): AuthRequestDto {
    return AuthRequestDto(
        email = this.email,
        password = this.password,
        nickname = this.nickname
    )
}

fun ItemDto.toDomain(): Item {
    val discountedUnitPrice = if (this.promotion == Promotion.ONE_PLUS_ONE) {
        this.pricePerGroup / 2
    } else {
        this.pricePerGroup / 3
    }
    return Item(
        id = this.id,
        name = this.name,
        imgUrl = this.imgUrl,
        brand = this.brand,
        promotion = this.promotion,
        pricePerUnit = this.pricePerUnit,
        pricePerGroup = this.pricePerGroup,
        discountedUnitPrice = discountedUnitPrice
    )
}

fun SearchItemResponseDto.toDomain(): SearchItemResponse {
    return SearchItemResponse(
        searchItems = this.searchItems.map { it.toDomain() }
    )
}


fun ChangeNicknameRequest.toDto(): ChangeNicknameRequestDto {
    return ChangeNicknameRequestDto(nickname = this.nickname)
}

fun ChangePasswordRequest.toDto(): ChangePasswordRequestDto {
    return ChangePasswordRequestDto(
        email = this.email,
        password = this.password,
        newPassword = this.newPassword
    )
}
fun ChangePasswordResponseDto.toDomain(): ChangePasswordResponse {
    return ChangePasswordResponse(result = this.result)
}

fun ReviewPageDto.toDomain(): ReviewPage {
    return ReviewPage(
        content = this.content.map { it.toDomain() },
        totalElements = this.totalElements,
        totalPages = this.totalPages,
        last = this.last,
        number = this.number,
        size = this.size
    )
}

fun ReviewResponseDto.toDomain(): ReviewResponse {
    return ReviewResponse(
        commentId = this.commentId,
        promotionId = this.promotionId,
        star = this.star,
        content = this.content,
        userName = this.userName,
        createdAt = this.createdAt,
        product = this.product.toDomain()
    )
}

fun ReviewProductDto.toDomain(): ReviewProduct {
    return ReviewProduct(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        brand = this.brand
    )
}

fun ReviewSummaryResponseDto.toDomain(): ReviewSummaryResponse {
    return ReviewSummaryResponse(
        totalCount = this.totalCount,
        averageRating = this.averageRating,
        ratingDistribution = this.ratingDistribution
    )
}

fun UpdateReviewBodyDto.toDomain(): UpdateReviewBody {
    return UpdateReviewBody(
        commentId = this.commentId,
        star = this.star,
        content = this.content
    )
}