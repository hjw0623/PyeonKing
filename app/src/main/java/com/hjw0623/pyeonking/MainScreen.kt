package com.hjw0623.pyeonking

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
import com.hjw0623.core.domain.auth.EmailPatternValidator
import com.hjw0623.core.domain.auth.UserDataValidator
import com.hjw0623.core.presentation.designsystem.components.BackBar
import com.hjw0623.core.presentation.ui.shouldShowBottomBar
import com.hjw0623.data.repository.AuthRepositoryImpl
import com.hjw0623.data.repository.ProductRepositoryImpl
import com.hjw0623.presentation.screen.auth.viewmodel.LoginViewModel
import com.hjw0623.presentation.screen.auth.viewmodel.RegisterViewModel
import com.hjw0623.presentation.screen.factory.HomeViewModelFactory
import com.hjw0623.presentation.screen.factory.LoginViewModelFactory
import com.hjw0623.presentation.screen.factory.RegisterViewModelFactory
import com.hjw0623.presentation.screen.home.ui.HomeScreenRoot
import com.hjw0623.presentation.screen.home.viewmodel.HomeViewModel
import com.hjw0623.presentation.screen.mypage.mypage_main.ui.MyPageScreenRoot
import com.hjw0623.presentation.screen.search.camera_search.ui.CameraScreenRoot
import com.hjw0623.presentation.screen.search.text_search.ui.TextSearchScreenRoot
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

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val bottomNavItems = remember { BottomNavItem.fetchBottomNavItems() }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val topBarData = navBackStackEntry?.topBarAsRouteName ?: TopBarData()
    val showBottomBar = shouldShowBottomBar(currentRoute)

    val loginViewModelFactory = LoginViewModelFactory(
        authRepository = AuthRepositoryImpl(),
        userDataValidator = UserDataValidator(EmailPatternValidator)
    )
    val loginViewModel: LoginViewModel = viewModel(factory = loginViewModelFactory)

    val registerViewModelFactory = RegisterViewModelFactory(
        authRepository = AuthRepositoryImpl(),
        userDataValidator = UserDataValidator(EmailPatternValidator)
    )
    val registerViewModel: RegisterViewModel = viewModel(factory = registerViewModelFactory)

    val homeViewModelFactory = HomeViewModelFactory(
        productRepository = ProductRepositoryImpl(),
    )
    val homeViewModel: HomeViewModel = viewModel(factory = homeViewModelFactory)

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
                        NavigationBarItem(
                            selected = currentRoute?.startsWith(bottomItem.destination.route) == true,
                            onClick = {
                                navController.navigate(bottomItem.destination.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
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
            startDestination = MainNavigationRoute.Home.route
        ) {
            composable(MainNavigationRoute.Home.route) {
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
            composable(MainNavigationRoute.Camera.route) {
                CameraScreenRoot(
                    onNavigateToSearchResult = { args ->
                        navController.navigate(CameraTabNestedRoute.SearchResult(args)) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(MainNavigationRoute.TextSearch.route) {
                TextSearchScreenRoot(
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
            composable(MainNavigationRoute.MyPage.route) {
                MyPageScreenRoot(
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
            homeNavGraph(navController)
            cameraNavGraph(navController)
            textSearchNavGraph(navController)
            myPageNavGraph(navController, loginViewModel, registerViewModel)
        }
    }
}