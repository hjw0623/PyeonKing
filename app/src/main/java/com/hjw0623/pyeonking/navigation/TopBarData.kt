package com.hjw0623.pyeonking.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import com.hjw0623.core.presentation.designsystem.theme.backgroundLight
import com.hjw0623.core.presentation.designsystem.theme.primaryLight

data class TopBarData(
    val title: String = "",
    val iconTint: Color = primaryLight,
    val backgroundColor: Color = backgroundLight,
    val icon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    val visible: Boolean = true
)

val NavBackStackEntry.topBarAsRouteName: TopBarData
    get() {
        val routeName = destination.route ?: return TopBarData()
        return when {
            routeName.contains("ReviewEdit") -> TopBarData(title = "리뷰 수정")
            routeName.contains("ReviewWrite") -> TopBarData(title = "리뷰 작성")
            routeName.contains("SearchResult") -> TopBarData(title = "검색 결과")
            routeName.contains("ProductDetail") -> TopBarData(
                iconTint = Color.White,
                backgroundColor = primaryLight
            )

            routeName.contains("RegisterSuccess") -> TopBarData(visible = false)

            routeName.contains("home") -> TopBarData(visible = false)
            routeName.contains("camera_search") -> TopBarData(visible = false)
            routeName.contains("text_search") -> TopBarData(visible = false)
            routeName.contains("mypage") -> TopBarData(visible = false)
            else -> TopBarData()
        }
    }
