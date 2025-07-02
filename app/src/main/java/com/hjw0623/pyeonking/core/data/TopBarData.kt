package com.hjw0623.pyeonking.core.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import com.hjw0623.pyeonking.core.presentation.ui.theme.backgroundLight
import com.hjw0623.pyeonking.core.presentation.ui.theme.primaryLight

data class TopBarData(
    val title: String = "",
    val iconTint: Color = primaryLight,
    val backgroundColor: Color = backgroundLight,
    val icon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    val visible: Boolean = true
)


val NavBackStackEntry.topBarAsRouteName: TopBarData
    get() {
        val normalizedRoute = destination.route?.lowercase() ?: ""

        return when {
            normalizedRoute.startsWith("home") -> TopBarData(visible = false)
            normalizedRoute.startsWith("camera") -> TopBarData(visible = false)
            normalizedRoute.startsWith("text_search") -> TopBarData(visible = false)
            normalizedRoute.startsWith("mypage") -> TopBarData(visible = false)

            "reviewedit" in normalizedRoute -> TopBarData(title = "리뷰 수정")
            "reviewwrite" in normalizedRoute -> TopBarData(title = "리뷰 작성")
            "searchresult" in normalizedRoute -> TopBarData(title = "검색 결과")
            "productdetail" in normalizedRoute -> TopBarData(
                iconTint = Color.White,
                backgroundColor = primaryLight
            )
            "registersuccess" in normalizedRoute -> TopBarData(visible = false)

            else -> TopBarData()
        }
    }
