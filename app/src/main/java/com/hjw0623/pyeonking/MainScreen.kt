package com.hjw0623.pyeonking

import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hjw0623.core.presentation.designsystem.components.BackBar
import com.hjw0623.core.presentation.ui.shouldShowBottomBar
import com.hjw0623.presentation.screen.auth.viewmodel.LoginViewModel
import com.hjw0623.presentation.screen.auth.viewmodel.RegisterViewModel
import com.hjw0623.presentation.screen.factory.PyeonKingViewModelFactory
import com.hjw0623.presentation.screen.home.ui.HomeScreenRoot
import com.hjw0623.presentation.screen.home.viewmodel.HomeViewModel
import com.hjw0623.presentation.screen.mypage.mypage_main.ui.MyPageScreenRoot
import com.hjw0623.presentation.screen.mypage.viewmodel.MyPageViewModel
import com.hjw0623.presentation.screen.product.viewmodel.ProductViewModel
import com.hjw0623.presentation.screen.review.viewmodel.ReviewEditViewModel
import com.hjw0623.presentation.screen.review.viewmodel.ReviewHistoryViewModel
import com.hjw0623.presentation.screen.review.viewmodel.ReviewWriteViewModel
import com.hjw0623.presentation.screen.search.camera_search.ui.CameraScreenRoot
import com.hjw0623.presentation.screen.search.text_search.ui.TextSearchScreenRoot
import com.hjw0623.presentation.screen.search.viewmodel.CameraSearchViewModel
import com.hjw0623.presentation.screen.search.viewmodel.SearchResultViewModel
import com.hjw0623.presentation.screen.search.viewmodel.TextSearchViewModel
import com.hjw0623.pyeonking.navigation.TopBarData
import com.hjw0623.pyeonking.navigation.bottom_nav.BottomNavItem
import com.hjw0623.pyeonking.navigation.nav_route.CameraTabNestedRoute
import com.hjw0623.pyeonking.navigation.nav_route.HomeTabNestedRoute
import com.hjw0623.pyeonking.navigation.nav_route.MainNavigationRoute
import com.hjw0623.pyeonking.navigation.nav_route.MyPageTabNestedRoute
import com.hjw0623.pyeonking.navigation.nav_route.TextSearchTabNestedRoute
import com.hjw0623.pyeonking.navigation.nav_route.cameraNavGraph
import com.hjw0623.pyeonking.navigation.nav_route.homeNavGraph
import com.hjw0623.pyeonking.navigation.nav_route.myPageNavGraph
import com.hjw0623.pyeonking.navigation.nav_route.textSearchNavGraph
import com.hjw0623.pyeonking.navigation.topBarAsRouteName
import kotlinx.serialization.json.internal.writeJson

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val bottomNavItems = remember { BottomNavItem.fetchBottomNavItems() }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val routeName = currentRoute?.substringAfterLast(".")
    val topBarData = navBackStackEntry?.topBarAsRouteName ?: TopBarData()
    val showBottomBar = shouldShowBottomBar(currentRoute)

    val loginViewModel = viewModel<LoginViewModel>(factory = PyeonKingViewModelFactory)

    val registerViewModel = viewModel<RegisterViewModel>(factory = PyeonKingViewModelFactory)

    val homeViewModel = viewModel<HomeViewModel>(factory = PyeonKingViewModelFactory)

    val myPageViewModel = viewModel<MyPageViewModel>(factory = PyeonKingViewModelFactory)

    val productViewModel = viewModel<ProductViewModel>(factory = PyeonKingViewModelFactory)

    val reviewEditViewModel = viewModel<ReviewEditViewModel>(factory = PyeonKingViewModelFactory)

    val reviewHistoryViewModel =
        viewModel<ReviewHistoryViewModel>(factory = PyeonKingViewModelFactory)

    val reviewWriteViewModel = viewModel<ReviewWriteViewModel>(factory = PyeonKingViewModelFactory)

    val cameraSearchViewModel =
        viewModel<CameraSearchViewModel>(factory = PyeonKingViewModelFactory)

    val searchResultViewModel =
        viewModel<SearchResultViewModel>(factory = PyeonKingViewModelFactory)

    val textSearchViewModel = viewModel<TextSearchViewModel>(factory = PyeonKingViewModelFactory)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (topBarData.visible) {
                BackBar(
                    title = topBarData.title,
                    iconTint = topBarData.iconTint,
                    backgroundColor = topBarData.backgroundColor,
                    onBackClick = { navController.popBackStack() },
                    icon = topBarData.icon,
                    modifier = Modifier.padding(
                        top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
                    )
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
                    bottomNavItems.forEach { bottomItem ->
                        val destinationName = bottomItem.destination::class.simpleName
                        NavigationBarItem(
                            selected = routeName == destinationName,
                            onClick = {
                                navController.navigate(bottomItem.destination) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(bottomItem.icon, contentDescription = bottomItem.tabName)
                            },
                            label = { Text(bottomItem.tabName) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                unselectedTextColor = MaterialTheme.colorScheme.outline,
                                unselectedIconColor = MaterialTheme.colorScheme.outline,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = navController,
            startDestination = MainNavigationRoute.Home
        ) {
            composable<MainNavigationRoute.Home> {
                HomeScreenRoot(
                    homeViewModel = homeViewModel,
                    onNavigateToProductDetail = { product ->
                        navController.navigate(HomeTabNestedRoute.ProductDetail(product)) {
                            launchSingleTop = true
                        }
                    },
                    onNavigateToSearchResult = { args ->
                        navController.navigate(HomeTabNestedRoute.SearchResult(args)) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<MainNavigationRoute.Camera> {
                CameraScreenRoot(
                    cameraSearchViewModel = cameraSearchViewModel,
                    onNavigateToSearchResult = { args ->
                        navController.navigate(CameraTabNestedRoute.SearchResult(args)) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<MainNavigationRoute.TextSearch> {
                TextSearchScreenRoot(
                    textSearchViewModel = textSearchViewModel,
                    onNavigateToSearchResult = { args ->
                        navController.navigate(TextSearchTabNestedRoute.SearchResult(args)) {
                            launchSingleTop = true
                        }
                    },
                    onNavigateToProductDetail = { product ->
                        navController.navigate(TextSearchTabNestedRoute.ProductDetail(product)) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<MainNavigationRoute.MyPage> {
                MyPageScreenRoot(
                    myPageViewModel = myPageViewModel,
                    onNavigateToChangeNickname = {
                        navController.navigate(MyPageTabNestedRoute.ChangeNickname) {
                            launchSingleTop = true
                        }
                    },
                    onNavigateToChangePassword = {
                        navController.navigate(MyPageTabNestedRoute.ChangePassword) {
                            launchSingleTop = true
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate(MyPageTabNestedRoute.Login) {
                            launchSingleTop = true
                        }
                    },
                    onNavigateToReviewHistory = {
                        navController.navigate(MyPageTabNestedRoute.ReviewHistory) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            homeNavGraph(
                navController,
                productViewModel,
                reviewWriteViewModel,
                searchResultViewModel
            )
            cameraNavGraph(
                navController,
                productViewModel,
                reviewWriteViewModel,
                searchResultViewModel
            )
            textSearchNavGraph(
                navController,
                productViewModel,
                reviewWriteViewModel,
                searchResultViewModel
            )
            myPageNavGraph(
                navController = navController,
                myPageViewModel = myPageViewModel,
                loginViewModel = loginViewModel,
                registerViewModel = registerViewModel,
                reviewEditViewModel = reviewEditViewModel,
                reviewHistoryViewModel = reviewHistoryViewModel
            )
        }
    }
}