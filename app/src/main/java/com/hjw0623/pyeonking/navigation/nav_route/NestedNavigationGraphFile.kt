package com.hjw0623.pyeonking.navigation.nav_route

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.hjw0623.pyeonking.auth.login.LoginScreenRoot
import com.hjw0623.pyeonking.auth.register.presentation.RegisterScreenRoot
import com.hjw0623.pyeonking.auth.register.presentation.RegisterSuccessScreen
import com.hjw0623.pyeonking.change_nickname.ChangeNicknameScreenRoot
import com.hjw0623.pyeonking.change_password.ChangePasswordScreenRoot
import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.SearchResultNavArgs
import com.hjw0623.pyeonking.core.util.parcelableType
import com.hjw0623.pyeonking.main_screen.mypage.presentation.MyPageScreenRoot
import com.hjw0623.pyeonking.product_detail.presentation.ProductDetailScreenRoot
import com.hjw0623.pyeonking.review.review_edit.ReviewEditScreenRoot
import com.hjw0623.pyeonking.review.review_history.data.ReviewInfo
import com.hjw0623.pyeonking.review.review_history.presentation.ReviewHistoryScreenRoot
import com.hjw0623.pyeonking.review.review_write.presentation.ReviewWriteScreenRoot
import com.hjw0623.pyeonking.search_result.presentation.SearchResultScreenRoot
import kotlin.reflect.typeOf


fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController
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
            onReviewWriteComplete = {
                navController.popBackStack()
            },
        )
    }
}

fun NavGraphBuilder.cameraNavGraph(
    navController: NavHostController
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
            onReviewWriteComplete = {
                navController.popBackStack()
            },
        )
    }
}

fun NavGraphBuilder.textSearchNavGraph(
    navController: NavHostController
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
            onReviewWriteComplete = {
                navController.popBackStack()
            },
        )
    }
}

fun NavGraphBuilder.myPageNavGraph(
    navController: NavHostController
) {
    composable<MyPageTabNestedRoute.MyPage> {
        MyPageScreenRoot(
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
            }
        )
    }

    composable<MyPageTabNestedRoute.Register> {
        RegisterScreenRoot(
            onNavigateToRegisterSuccess = {
                navController.navigate(MyPageTabNestedRoute.RegisterSuccess)
            }
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
            onNavigateToMyPage = {
                navController.popBackStack()
            }
        )
    }

    composable<MyPageTabNestedRoute.ChangePassword> {
        ChangePasswordScreenRoot(
            onNavigateToMyPage = {
                navController.popBackStack()
            }
        )
    }

    composable<MyPageTabNestedRoute.ReviewHistory> {
        ReviewHistoryScreenRoot(
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
            onNavigateReviewHistory = {
                navController.popBackStack()
            }
        )
    }
}
