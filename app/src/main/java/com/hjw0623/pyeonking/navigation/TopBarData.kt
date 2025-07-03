package com.hjw0623.pyeonking.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import com.hjw0623.core.constants.EmptyString
import com.hjw0623.core.constants.ScreenRoutes
import com.hjw0623.core.constants.TopBarTitle
import com.hjw0623.core.presentation.designsystem.theme.backgroundLight
import com.hjw0623.core.presentation.designsystem.theme.primaryLight

data class TopBarData(
    val title: String = EmptyString.EMPTY_STRING,
    val iconTint: Color = primaryLight,
    val backgroundColor: Color = backgroundLight,
    val icon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
    val visible: Boolean = true
)

val NavBackStackEntry.topBarAsRouteName: TopBarData
    get() {
        val routeName = destination.route ?: return TopBarData()
        return when {
            routeName.contains(ScreenRoutes.REVIEW_EDIT) -> TopBarData(title = TopBarTitle.TITLE_REVIEW_EDIT)
            routeName.contains(ScreenRoutes.REVIEW_WRITE) -> TopBarData(title = TopBarTitle.TITLE_REVIEW_WRITE)
            routeName.contains(ScreenRoutes.SEARCH_RESULT) -> TopBarData(title = TopBarTitle.TITLE_SEARCH_RESULT)
            routeName.contains(ScreenRoutes.PRODUCT_DETAIL) -> TopBarData(
                iconTint = Color.White, backgroundColor = primaryLight
            )

            routeName.contains(ScreenRoutes.REGISTER_SUCCESS) -> TopBarData(visible = false)
            routeName.contains(ScreenRoutes.HOME) -> TopBarData(visible = false)
            routeName.contains(ScreenRoutes.CAMERA_SEARCH) -> TopBarData(visible = false)
            routeName.contains(ScreenRoutes.TEXT_SEARCH) -> TopBarData(visible = false)
            routeName.contains(ScreenRoutes.MYPAGE) -> TopBarData(visible = false)
            else -> TopBarData()
        }
    }

