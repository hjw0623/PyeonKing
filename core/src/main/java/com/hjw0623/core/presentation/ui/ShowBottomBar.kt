package com.hjw0623.core.presentation.ui

import com.hjw0623.core.util.constants.ScreenRoutes

fun shouldShowBottomBar(route: String?): Boolean {
    return route in listOf(
        ScreenRoutes.HOME,
        ScreenRoutes.CAMERA_SEARCH,
        ScreenRoutes.TEXT_SEARCH,
        ScreenRoutes.MYPAGE
    )
}