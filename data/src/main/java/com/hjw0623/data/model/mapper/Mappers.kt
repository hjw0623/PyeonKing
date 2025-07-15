package com.hjw0623.data.model.mapper

import com.hjw0623.core.data.model.AuthRequest
import com.hjw0623.core.data.model.AuthResponse
import com.hjw0623.data.model.AuthRequestDto
import com.hjw0623.data.model.AuthResponseDto


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