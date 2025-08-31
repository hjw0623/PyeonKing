package com.hjw0623.core.ui.util

fun shouldShowBottomBar(route: String?): Boolean {
    val simpleName = route?.substringAfterLast(".") ?: return false
    return simpleName in listOf("Home", "Camera", "TextSearch", "MyPage")
}
