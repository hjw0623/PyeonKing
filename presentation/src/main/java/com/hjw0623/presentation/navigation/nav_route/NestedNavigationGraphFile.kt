package com.hjw0623.presentation.navigation.nav_route

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hjw0623.core.core_domain.model.product.Product
import com.hjw0623.core.core_domain.model.review.ReviewInfo
import com.hjw0623.core.core_domain.model.search.search_result.SearchResultNavArgs
import com.hjw0623.core.android.constants.NavArgs
import com.hjw0623.presentation.navigation.parcelableType
import com.hjw0623.presentation.screen.auth.login.ui.LoginScreenRoot
import com.hjw0623.presentation.screen.auth.register.ui.RegisterScreenRoot
import com.hjw0623.presentation.screen.auth.register.ui.RegisterSuccessScreen
import com.hjw0623.presentation.screen.auth.viewmodel.LoginViewModel
import com.hjw0623.presentation.screen.auth.viewmodel.RegisterViewModel
import com.hjw0623.presentation.screen.mypage.change_nickname.ui.ChangeNicknameScreenRoot
import com.hjw0623.presentation.screen.mypage.change_password.ui.ChangePasswordScreenRoot
import com.hjw0623.presentation.screen.mypage.mypage_main.ui.MyPageScreenRoot
import com.hjw0623.presentation.screen.mypage.viewmodel.MyPageViewModel
import com.hjw0623.presentation.screen.product.ui.ProductDetailScreenRoot
import com.hjw0623.presentation.screen.product.viewmodel.ProductViewModel
import com.hjw0623.presentation.screen.review.review_edit.ui.ReviewEditScreenRoot
import com.hjw0623.presentation.screen.review.review_history.ui.ReviewHistoryScreenRoot
import com.hjw0623.presentation.screen.review.review_write.ui.ReviewWriteScreenRoot
import com.hjw0623.presentation.screen.review.viewmodel.ReviewEditViewModel
import com.hjw0623.presentation.screen.review.viewmodel.ReviewHistoryViewModel
import com.hjw0623.presentation.screen.review.viewmodel.ReviewWriteViewModel
import com.hjw0623.presentation.screen.search.search_result.ui.SearchResultScreenRoot
import com.hjw0623.presentation.screen.search.viewmodel.SearchResultViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.reflect.typeOf

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
) {
    composable<HomeTabNestedRoute.SearchResult>(
        typeMap = mapOf(typeOf<SearchResultNavArgs>() to parcelableType<SearchResultNavArgs>())
    ) { backStackEntry ->
        val vm: SearchResultViewModel = hiltViewModel(backStackEntry)
        val navArgs = backStackEntry.toRoute<HomeTabNestedRoute.SearchResult>().searchResultNavArgs
        SearchResultScreenRoot(
            navArgs = navArgs,
            searchResultViewModel = vm,
            onNavigateToProductDetail = { product ->
                navController.navigate(HomeTabNestedRoute.ProductDetail(product))
            },
        )
    }

    composable<HomeTabNestedRoute.ProductDetail>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) { backStackEntry ->
        val vm: ProductViewModel = hiltViewModel(backStackEntry)
        val product = backStackEntry.toRoute<HomeTabNestedRoute.ProductDetail>().product

        val refreshFlow = remember(backStackEntry) {
            backStackEntry.savedStateHandle.getStateFlow(NavArgs.REVIEW_REFRESH_FLAG, false)
        }

        LaunchedEffect(Unit) {
            refreshFlow.collectLatest { shouldRefresh ->
                if (shouldRefresh) {
                    vm.refreshReviews(product)
                    backStackEntry.savedStateHandle[NavArgs.REVIEW_REFRESH_FLAG] = false
                }
            }
        }

        ProductDetailScreenRoot(
            product = product,
            productViewModel = vm,
            onNavigateToReviewWrite = { productData ->
                navController.navigate(HomeTabNestedRoute.ReviewWrite(productData))
            }
        )
    }

    composable<HomeTabNestedRoute.ReviewWrite>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) { backStackEntry ->
        val vm: ReviewWriteViewModel = hiltViewModel(backStackEntry)
        val product = backStackEntry.toRoute<HomeTabNestedRoute.ReviewWrite>().product
        ReviewWriteScreenRoot(
            product = product,
            reviewWriteViewModel = vm,
            onReviewWriteComplete = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(NavArgs.REVIEW_REFRESH_FLAG, true)
                navController.popBackStack()
            },
        )
    }
}

fun NavGraphBuilder.cameraNavGraph(
    navController: NavHostController,
) {
    composable<CameraTabNestedRoute.SearchResult>(
        typeMap = mapOf(typeOf<SearchResultNavArgs>() to parcelableType<SearchResultNavArgs>())
    ) { backStackEntry ->
        val vm: SearchResultViewModel = hiltViewModel(backStackEntry)
        val navArgs =
            backStackEntry.toRoute<CameraTabNestedRoute.SearchResult>().searchResultNavArgs
        SearchResultScreenRoot(
            navArgs = navArgs,
            searchResultViewModel = vm,
            onNavigateToProductDetail = { product ->
                navController.navigate(CameraTabNestedRoute.ProductDetail(product))
            }
        )
    }

    composable<CameraTabNestedRoute.ProductDetail>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) { backStackEntry ->
        val vm: ProductViewModel = hiltViewModel(backStackEntry)
        val product = backStackEntry.toRoute<CameraTabNestedRoute.ProductDetail>().product

        val refreshFlow = remember(backStackEntry) {
            backStackEntry.savedStateHandle.getStateFlow(NavArgs.REVIEW_REFRESH_FLAG, false)
        }

        LaunchedEffect(Unit) {
            refreshFlow.collectLatest { shouldRefresh ->
                if (shouldRefresh) {
                    vm.refreshReviews(product)
                    backStackEntry.savedStateHandle[NavArgs.REVIEW_REFRESH_FLAG] = false
                }
            }
        }

        ProductDetailScreenRoot(
            product = product,
            productViewModel = vm,
            onNavigateToReviewWrite = { productData ->
                navController.navigate(CameraTabNestedRoute.ReviewWrite(productData))
            }
        )
    }

    composable<CameraTabNestedRoute.ReviewWrite>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) { backStackEntry ->
        val vm: ReviewWriteViewModel = hiltViewModel(backStackEntry)
        val product = backStackEntry.toRoute<CameraTabNestedRoute.ReviewWrite>().product
        ReviewWriteScreenRoot(
            product = product,
            reviewWriteViewModel = vm,
            onReviewWriteComplete = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(NavArgs.REVIEW_REFRESH_FLAG, true)
                navController.popBackStack()
            },
        )
    }
}

fun NavGraphBuilder.textSearchNavGraph(
    navController: NavHostController,
) {
    composable<TextSearchTabNestedRoute.SearchResult>(
        typeMap = mapOf(typeOf<SearchResultNavArgs>() to parcelableType<SearchResultNavArgs>())
    ) { backStackEntry ->
        val vm: SearchResultViewModel = hiltViewModel(backStackEntry)
        val navArgs =
            backStackEntry.toRoute<TextSearchTabNestedRoute.SearchResult>().searchResultNavArgs
        SearchResultScreenRoot(
            navArgs = navArgs,
            searchResultViewModel = vm,
            onNavigateToProductDetail = { product ->
                navController.navigate(TextSearchTabNestedRoute.ProductDetail(product))
            },
        )
    }

    composable<TextSearchTabNestedRoute.ProductDetail>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) { backStackEntry ->
        val vm: ProductViewModel = hiltViewModel(backStackEntry)
        val product = backStackEntry.toRoute<TextSearchTabNestedRoute.ProductDetail>().product

        val refreshFlow = remember(backStackEntry) {
            backStackEntry.savedStateHandle.getStateFlow(NavArgs.REVIEW_REFRESH_FLAG, false)
        }

        LaunchedEffect(Unit) {
            refreshFlow.collectLatest { shouldRefresh ->
                if (shouldRefresh) {
                    vm.refreshReviews(product)
                    backStackEntry.savedStateHandle[NavArgs.REVIEW_REFRESH_FLAG] = false
                }
            }
        }

        ProductDetailScreenRoot(
            product = product,
            productViewModel = vm,
            onNavigateToReviewWrite = { productData ->
                navController.navigate(TextSearchTabNestedRoute.ReviewWrite(productData))
            },
        )
    }

    composable<TextSearchTabNestedRoute.ReviewWrite>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) { backStackEntry ->
        val vm: ReviewWriteViewModel = hiltViewModel(backStackEntry)
        val product = backStackEntry.toRoute<TextSearchTabNestedRoute.ReviewWrite>().product
        ReviewWriteScreenRoot(
            product = product,
            reviewWriteViewModel = vm,
            onReviewWriteComplete = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(NavArgs.REVIEW_REFRESH_FLAG, true)
                navController.popBackStack()
            },
        )
    }
}

fun NavGraphBuilder.myPageNavGraph(
    navController: NavHostController,
) {
    composable<MyPageTabNestedRoute.MyPage> { backStackEntry ->
        val vm: MyPageViewModel = hiltViewModel(backStackEntry)
        MyPageScreenRoot(
            myPageViewModel = vm,
            onNavigateToChangeNickname = { navController.navigate(MyPageTabNestedRoute.ChangeNickname) },
            onNavigateToChangePassword = { navController.navigate(MyPageTabNestedRoute.ChangePassword) },
            onNavigateToLogin = { navController.navigate(MyPageTabNestedRoute.Login) },
            onNavigateToReviewHistory = { navController.navigate(MyPageTabNestedRoute.ReviewHistory) }
        )
    }

    composable<MyPageTabNestedRoute.Login> { backStackEntry ->
        val vm: LoginViewModel = hiltViewModel(backStackEntry)
        LoginScreenRoot(
            onNavigateToRegister = { navController.navigate(MyPageTabNestedRoute.Register) },
            onNavigateToMyPage = {
                navController.navigate(MyPageTabNestedRoute.MyPage) {
                    popUpTo(MyPageTabNestedRoute.MyPage) { inclusive = true }
                }
            },
            loginViewModel = vm
        )
    }

    composable<MyPageTabNestedRoute.Register> { backStackEntry ->
        val vm: RegisterViewModel = hiltViewModel(backStackEntry)
        RegisterScreenRoot(
            onNavigateToRegisterSuccess = { navController.navigate(MyPageTabNestedRoute.RegisterSuccess) },
            registerViewModel = vm
        )
    }

    composable<MyPageTabNestedRoute.RegisterSuccess> {
        RegisterSuccessScreen(
            onNavigateToLogin = {
                navController.navigate(MyPageTabNestedRoute.Login) {
                    popUpTo(MyPageTabNestedRoute.Login) { inclusive = true }
                }
            }
        )
    }

    composable<MyPageTabNestedRoute.ChangeNickname> { backStackEntry ->
        val vm: MyPageViewModel = hiltViewModel(backStackEntry)
        ChangeNicknameScreenRoot(
            myPageViewModel = vm,
            onNavigateToMyPage = { navController.popBackStack() }
        )
    }

    composable<MyPageTabNestedRoute.ChangePassword> { backStackEntry ->
        val vm: MyPageViewModel = hiltViewModel(backStackEntry)
        ChangePasswordScreenRoot(
            myPageViewModel = vm,
            onNavigateToMyPage = { navController.popBackStack() }
        )
    }

    composable<MyPageTabNestedRoute.ReviewHistory> { backStackEntry ->
        val vm: ReviewHistoryViewModel = hiltViewModel(backStackEntry)
        ReviewHistoryScreenRoot(
            reviewHistoryViewModel = vm,
            onNavigateToReviewEdit = { reviewInfo ->
                navController.navigate(MyPageTabNestedRoute.ReviewEdit(reviewInfo))
            }
        )
    }

    composable<MyPageTabNestedRoute.ReviewEdit>(
        typeMap = mapOf(typeOf<ReviewInfo>() to parcelableType<ReviewInfo>())
    ) { backStackEntry ->
        val vm: ReviewEditViewModel = hiltViewModel(backStackEntry)
        val reviewInfo = backStackEntry.toRoute<MyPageTabNestedRoute.ReviewEdit>().reviewInfo
        ReviewEditScreenRoot(
            reviewInfo = reviewInfo,
            reviewEditViewModel = vm,
            onNavigateReviewHistory = { navController.popBackStack() }
        )
    }
}
