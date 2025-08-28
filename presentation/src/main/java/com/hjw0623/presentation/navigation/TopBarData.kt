package com.hjw0623.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import com.hjw0623.core.core_andriod.constants.EmptyString
import com.hjw0623.core.core_andriod.constants.ScreenRoutes
import com.hjw0623.core.core_andriod.constants.TopBarTitle
import com.hjw0623.core.core_ui.designsystem.theme.backgroundLight
import com.hjw0623.core.core_ui.designsystem.theme.primaryLight

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
        val simpleRoute = routeName.substringAfterLast(".")

        return when (simpleRoute) {
            ScreenRoutes.REVIEW_EDIT -> TopBarData(title = TopBarTitle.TITLE_REVIEW_EDIT)
            ScreenRoutes.REVIEW_WRITE -> TopBarData(title = TopBarTitle.TITLE_REVIEW_WRITE)
            ScreenRoutes.SEARCH_RESULT -> TopBarData(title = TopBarTitle.TITLE_SEARCH_RESULT)
            ScreenRoutes.PRODUCT_DETAIL -> TopBarData(
                iconTint = Color.White, backgroundColor = primaryLight
            )

            ScreenRoutes.REGISTER_SUCCESS -> TopBarData(visible = false)
            ScreenRoutes.HOME -> TopBarData(visible = false)
            ScreenRoutes.CAMERA_SEARCH -> TopBarData(visible = false)
            ScreenRoutes.TEXT_SEARCH -> TopBarData(visible = false)
            ScreenRoutes.MY_PAGE -> TopBarData(visible = false)

            else -> TopBarData()
        }
    }


