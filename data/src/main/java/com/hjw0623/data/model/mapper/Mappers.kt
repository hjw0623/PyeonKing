package com.hjw0623.data.model.mapper

import com.hjw0623.core.constants.Promotion
import com.hjw0623.core.data.model.AuthRequest
import com.hjw0623.core.data.model.AuthResponse
import com.hjw0623.core.data.model.ChangeNicknameRequest
import com.hjw0623.core.data.model.ChangePasswordRequest
import com.hjw0623.core.data.model.ChangePasswordResponse
import com.hjw0623.core.data.model.Item
import com.hjw0623.core.data.model.SearchItemResponse
import com.hjw0623.data.model.AuthRequestDto
import com.hjw0623.data.model.AuthResponseDto
import com.hjw0623.data.model.ChangeNicknameRequestDto
import com.hjw0623.data.model.ChangePasswordRequestDto
import com.hjw0623.data.model.ChangePasswordResponseDto
import com.hjw0623.data.model.ItemDto
import com.hjw0623.data.model.SearchItemResponseDto


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