package com.hjw0623.pyeonking.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.hjw0623.pyeonking.nav_route.MainNavigationRoute

data class BottomNavItem(
    val tabName: String,
    val icon: ImageVector,
    val destination: MainNavigationRoute
) {
    companion object {
        fun fetchBottomNavItems(): List<BottomNavItem> = listOf(
            BottomNavItem("홈", Icons.Default.Home, MainNavigationRoute.Home),
            BottomNavItem("카메라 검색", Icons.Default.CameraAlt, MainNavigationRoute.Camera),
            BottomNavItem("검색", Icons.Default.Search, MainNavigationRoute.TextSearch),
            BottomNavItem("마이페이지", Icons.Default.Person, MainNavigationRoute.MyPage)
        )
    }
}
