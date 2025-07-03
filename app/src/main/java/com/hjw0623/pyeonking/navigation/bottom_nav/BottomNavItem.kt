package com.hjw0623.pyeonking.navigation.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.hjw0623.core.constants.MainScreenTitle
import com.hjw0623.pyeonking.navigation.nav_route.MainNavigationRoute

data class BottomNavItem(
    val tabName: String,
    val icon: ImageVector,
    val destination: MainNavigationRoute
) {
    companion object {
        fun fetchBottomNavItems(): List<BottomNavItem> = listOf(
            BottomNavItem(
                MainScreenTitle.HOME,
                Icons.Default.Home,
                MainNavigationRoute.Home
            ),
            BottomNavItem(
                MainScreenTitle.CAMERA_SEARCH,
                Icons.Default.CameraAlt,
                MainNavigationRoute.Camera
            ),
            BottomNavItem(
                MainScreenTitle.TEXT_SEARCH,
                Icons.Default.Search,
                MainNavigationRoute.TextSearch
            ),
            BottomNavItem(
                MainScreenTitle.MYPAGE,
                Icons.Default.Person,
                MainNavigationRoute.MyPage
            )
        )
    }
}
