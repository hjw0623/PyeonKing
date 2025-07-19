package com.hjw0623.pyeonking.navigation.nav_route

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.review.review_history.ReviewInfo
import com.hjw0623.core.domain.search.search_result.SearchResultNavArgs
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
import com.hjw0623.pyeonking.navigation.parcelableType
import kotlin.reflect.typeOf


fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    reviewWriteViewModel: ReviewWriteViewModel
) {
    composable<HomeTabNestedRoute.SearchResult>(
        typeMap = mapOf(typeOf<SearchResultNavArgs>() to parcelableType<SearchResultNavArgs>())
    ) {
        val navArgs = it.toRoute<HomeTabNestedRoute.SearchResult>().searchResultNavArgs
        SearchResultScreenRoot(
            navArgs = navArgs,
            onNavigateToProductDetail = { product ->
                navController.navigate(HomeTabNestedRoute.ProductDetail(product))
            },
        )
    }

    composable<HomeTabNestedRoute.ProductDetail>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) {
        val product = it.toRoute<HomeTabNestedRoute.ProductDetail>().product
        ProductDetailScreenRoot(
            product = product,
            productViewModel = productViewModel,
            onNavigateToReviewWrite = { productData ->
                navController.navigate(HomeTabNestedRoute.ReviewWrite(productData))
            }
        )
    }

    composable<HomeTabNestedRoute.ReviewWrite>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) {
        val product = it.toRoute<HomeTabNestedRoute.ReviewWrite>().product
        ReviewWriteScreenRoot(
            product = product,
            reviewWriteViewModel = reviewWriteViewModel,
            onReviewWriteComplete = {
                navController.popBackStack()
            },
        )
    }
}

fun NavGraphBuilder.cameraNavGraph(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    reviewWriteViewModel: ReviewWriteViewModel
) {
    composable<CameraTabNestedRoute.SearchResult>(
        typeMap = mapOf(typeOf<SearchResultNavArgs>() to parcelableType<SearchResultNavArgs>())
    ) {
        val navArgs = it.toRoute<CameraTabNestedRoute.SearchResult>().searchResultNavArgs
        SearchResultScreenRoot(
            navArgs = navArgs,
            onNavigateToProductDetail = { product ->
                navController.navigate(CameraTabNestedRoute.ProductDetail(product))
            }
        )
    }

    composable<CameraTabNestedRoute.ProductDetail>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) {
        val product = it.toRoute<CameraTabNestedRoute.ProductDetail>().product
        ProductDetailScreenRoot(
            product = product,
            productViewModel,
            onNavigateToReviewWrite = { productData ->
                navController.navigate(CameraTabNestedRoute.ReviewWrite(productData))
            }
        )
    }

    composable<CameraTabNestedRoute.ReviewWrite>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) {
        val product = it.toRoute<CameraTabNestedRoute.ReviewWrite>().product
        ReviewWriteScreenRoot(
            product = product,
            reviewWriteViewModel = reviewWriteViewModel,
            onReviewWriteComplete = {
                navController.popBackStack()
            },
        )
    }
}

fun NavGraphBuilder.textSearchNavGraph(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    reviewWriteViewModel: ReviewWriteViewModel
) {
    composable<TextSearchTabNestedRoute.SearchResult>(
        typeMap = mapOf(typeOf<SearchResultNavArgs>() to parcelableType<SearchResultNavArgs>())
    ) {
        val navArgs = it.toRoute<TextSearchTabNestedRoute.SearchResult>().searchResultNavArgs
        SearchResultScreenRoot(
            navArgs = navArgs,
            onNavigateToProductDetail = { product ->
                navController.navigate(TextSearchTabNestedRoute.ProductDetail(product))
            },
        )
    }

    composable<TextSearchTabNestedRoute.ProductDetail>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) {
        val product = it.toRoute<TextSearchTabNestedRoute.ProductDetail>().product
        ProductDetailScreenRoot(
            product = product,
            productViewModel = productViewModel,
            onNavigateToReviewWrite = { productData ->
                navController.navigate(TextSearchTabNestedRoute.ReviewWrite(productData))
            },
        )
    }

    composable<TextSearchTabNestedRoute.ReviewWrite>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) {
        val product = it.toRoute<TextSearchTabNestedRoute.ReviewWrite>().product
        ReviewWriteScreenRoot(
            product = product,
            reviewWriteViewModel = reviewWriteViewModel,
            onReviewWriteComplete = {
                navController.popBackStack()
            },
        )
    }
}

fun NavGraphBuilder.myPageNavGraph(
    navController: NavHostController,
    myPageViewModel: MyPageViewModel,
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    reviewEditViewModel: ReviewEditViewModel,
    reviewHistoryViewModel: ReviewHistoryViewModel
) {
    composable<MyPageTabNestedRoute.MyPage> {
        MyPageScreenRoot(
            myPageViewModel = myPageViewModel,
            onNavigateToChangeNickname = {
                navController.navigate(MyPageTabNestedRoute.ChangeNickname)
            },
            onNavigateToChangePassword = {
                navController.navigate(MyPageTabNestedRoute.ChangePassword)
            },
            onNavigateToLogin = {
                navController.navigate(MyPageTabNestedRoute.Login)
            },
            onNavigateToReviewHistory = {
                navController.navigate(MyPageTabNestedRoute.ReviewHistory)
            }
        )
    }

    composable<MyPageTabNestedRoute.Login> {
        LoginScreenRoot(
            onNavigateToRegister = {
                navController.navigate(MyPageTabNestedRoute.Register)
            },
            onNavigateToMyPage = {
                navController.navigate(MyPageTabNestedRoute.MyPage) {
                    popUpTo(MyPageTabNestedRoute.MyPage.javaClass.simpleName) { inclusive = true }
                }
            },
            loginViewModel = loginViewModel
        )
    }

    composable<MyPageTabNestedRoute.Register> {
        RegisterScreenRoot(
            onNavigateToRegisterSuccess = {
                navController.navigate(MyPageTabNestedRoute.RegisterSuccess)
            },
            registerViewModel = registerViewModel
        )
    }

    composable<MyPageTabNestedRoute.RegisterSuccess> {
        RegisterSuccessScreen(
            onNavigateToLogin = {
                navController.navigate(MyPageTabNestedRoute.Login) {
                    popUpTo(MyPageTabNestedRoute.Login) {
                        inclusive = true
                    }
                }
            }
        )
    }

    composable<MyPageTabNestedRoute.ChangeNickname> {
        ChangeNicknameScreenRoot(
            myPageViewModel = myPageViewModel,
            onNavigateToMyPage = {
                navController.popBackStack()
            }
        )
    }

    composable<MyPageTabNestedRoute.ChangePassword> {
        ChangePasswordScreenRoot(
            myPageViewModel = myPageViewModel,
            onNavigateToMyPage = {
                navController.popBackStack()
            }
        )
    }

    composable<MyPageTabNestedRoute.ReviewHistory> {
        ReviewHistoryScreenRoot(
            reviewHistoryViewModel = reviewHistoryViewModel,
            onNavigateToReviewEdit = { reviewInfo ->
                navController.navigate(MyPageTabNestedRoute.ReviewEdit(reviewInfo))
            }
        )
    }

    composable<MyPageTabNestedRoute.ReviewEdit>(
        typeMap = mapOf(typeOf<ReviewInfo>() to parcelableType<ReviewInfo>())
    ) {
        val reviewInfo = it.toRoute<MyPageTabNestedRoute.ReviewEdit>().reviewInfo
        ReviewEditScreenRoot(
            reviewInfo = reviewInfo,
            reviewEditViewModel = reviewEditViewModel,
            onNavigateReviewHistory = {
                navController.popBackStack()
            }
        )
    }
}
