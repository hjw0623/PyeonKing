package com.hjw0623.core.presentation.ui

import com.hjw0623.core.constants.ScreenRoutes

fun shouldShowBottomBar(route: String?): Boolean {
    val simpleName = route?.substringAfterLast(".") ?: return false
    return simpleName in listOf("Home", "Camera", "TextSearch", "MyPage")
}
