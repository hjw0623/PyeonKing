package com.hjw0623.presentation.navigation.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.hjw0623.core.constants.ui.BottomTabTitle
import com.hjw0623.presentation.navigation.nav_route.MainNavigationRoute

data class BottomNavItem(
    val tabName: String,
    val icon: ImageVector,
    val destination: MainNavigationRoute
) {
    companion object {
        fun fetchBottomNavItems(): List<BottomNavItem> = listOf(
            BottomNavItem(
                BottomTabTitle.HOME,
                Icons.Default.Home,
                MainNavigationRoute.Home
            ),
            BottomNavItem(
                BottomTabTitle.CAMERA_SEARCH,
                Icons.Default.CameraAlt,
                MainNavigationRoute.Camera
            ),
            BottomNavItem(
                BottomTabTitle.TEXT_SEARCH,
                Icons.Default.Search,
                MainNavigationRoute.TextSearch
            ),
            BottomNavItem(
                BottomTabTitle.MY_PAGE,
                Icons.Default.Person,
                MainNavigationRoute.MyPage
            )
        )
    }
}
