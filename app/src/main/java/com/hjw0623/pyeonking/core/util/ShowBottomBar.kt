package com.hjw0623.pyeonking.core.util

fun shouldShowBottomBar(route: String?): Boolean {
    val normalizedRoute = route?.lowercase() ?: return false

    return normalizedRoute in listOf(
        "home", "camera", "text_search", "mypage"
    )
}