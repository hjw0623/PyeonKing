package com.hjw0623.core.core_ui.ui

fun shouldShowBottomBar(route: String?): Boolean {
    val simpleName = route?.substringAfterLast(".") ?: return false
    return simpleName in listOf("Home", "Camera", "TextSearch", "MyPage")
}
