package com.hjw0623.core.presentation.ui

fun shouldShowBottomBar(route: String?): Boolean {
    val normalizedRoute = route?.lowercase() ?: return false

    return normalizedRoute in listOf(
        "home", "camera_search", "text_search", "mypage"
    )
}