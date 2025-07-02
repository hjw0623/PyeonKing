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
    composable<HomeNestedRoute.ProductDetail>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) {
        val product = it.toRoute<HomeNestedRoute.ProductDetail>().product
        ProductDetailScreenRoot(
            product = product,
            onNavigateToReviewWrite = {
                navController.navigate(ProductDetailNestedRoute.ReviewWrite(product))
            }
        )
    }

    composable<HomeNestedRoute.SearchResult>(
        typeMap = mapOf(typeOf<SearchResultNavArgs>() to parcelableType<SearchResultNavArgs>())
    ) {
        val searchResultNavArgs = it.toRoute<HomeNestedRoute.SearchResult>().searchResultNavArgs
        SearchResultScreenRoot(
            navArgs = searchResultNavArgs,
            onNavigateToProductDetail = { product ->
                navController.navigate(SearchResultNestedRoute.ProductDetail(product))
            },
        )
    }
}

fun NavGraphBuilder.cameraNavGraph(
    navController: NavHostController
) {
    composable<CameraNestedRoute.SearchResult>(
        typeMap = mapOf(typeOf<SearchResultNavArgs>() to parcelableType<SearchResultNavArgs>())
    ) {
        val searchResultNavArgs = it.toRoute<CameraNestedRoute.SearchResult>().searchResultNavArgs
        SearchResultScreenRoot(
            navArgs = searchResultNavArgs,
            onNavigateToProductDetail = { product ->
                navController.navigate(SearchResultNestedRoute.ProductDetail(product))
            }
        )
    }
}

fun NavGraphBuilder.textSearchNavGraph(
    navController: NavHostController
) {
    composable<TextSearchNestedRoute.SearchResult>(
        typeMap = mapOf(typeOf<SearchResultNavArgs>() to parcelableType<SearchResultNavArgs>())
    ) {
        val searchResultNavArgs =
            it.toRoute<TextSearchNestedRoute.SearchResult>().searchResultNavArgs
        SearchResultScreenRoot(
            navArgs = searchResultNavArgs,
            onNavigateToProductDetail = { product ->
                navController.navigate(SearchResultNestedRoute.ProductDetail(product))
            },
        )
    }

    composable<TextSearchNestedRoute.ProductDetail>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) {
        val product = it.toRoute<TextSearchNestedRoute.ProductDetail>().product
        ProductDetailScreenRoot(
            product = product,
            onNavigateToReviewWrite = {
                navController.navigate(ProductDetailNestedRoute.ReviewWrite(product))
            },
        )
    }
}

fun NavGraphBuilder.myPageNavGraph(
    navController: NavHostController
) {
    composable<MyPageNestedRoute.ChangeNickname> {
        ChangeNicknameScreenRoot(
            onNavigateToMyPage = {
                navController.navigate(ChangeNicknameNestedRoute.MyPage)
            }
        )
    }
    composable<MyPageNestedRoute.ChangePassword> {
        ChangePasswordScreenRoot(
            onNavigateToMyPage = {
                navController.navigate(ChangeNicknameNestedRoute.MyPage)
            }
        )
    }
    composable<MyPageNestedRoute.ReviewHistory> {
        ReviewHistoryScreenRoot(
            onNavigateToReviewEdit = { reviewInfo ->
                navController.navigate(ReviewHistoryNestedRoute.ReviewEdit(reviewInfo))
            }
        )
    }
    composable<MyPageNestedRoute.Login> {
        LoginScreenRoot(
            onNavigateToRegister = {
                navController.navigate(LoginNestedRoute.Register)
            },
            onNavigateToMyPage = {
                navController.navigate(ChangeNicknameNestedRoute.MyPage)
            }
        )
    }
}

fun NavGraphBuilder.loginNavGraph(
    navController: NavHostController
) {
    composable<LoginNestedRoute.Register> {
        RegisterScreenRoot(
            onNavigateToRegisterSuccess = {
                navController.navigate(RegisterNestedRoute.RegisterSuccess)
            }
        )
    }
    composable<LoginNestedRoute.MyPage> {
        MyPageScreenRoot(
            onNavigateToChangeNickname = {
                navController.navigate(MyPageNestedRoute.ChangeNickname)
            },
            onNavigateToChangePassword = {
                navController.navigate(MyPageNestedRoute.ChangePassword)
            },
            onNavigateToLogin = {
                navController.navigate(MyPageNestedRoute.Login)
            },
            onNavigateToReviewHistory = {
                navController.navigate(MyPageNestedRoute.ReviewHistory)
            }
        )
    }
}

fun NavGraphBuilder.registerNavGraph(
    navController: NavHostController
) {
    composable<RegisterNestedRoute.Register> {
        RegisterScreenRoot(
            onNavigateToRegisterSuccess = {
                navController.navigate(RegisterNestedRoute.RegisterSuccess)
            }
        )
    }

    composable<RegisterNestedRoute.RegisterSuccess> {
        RegisterSuccessScreen(
            onNavigateToLogin = {
                navController.navigate(RegisterSuccessNestedRoute.Login)
            }
        )
    }
}

fun NavGraphBuilder.changeNicknameNavGraph(
    navController: NavHostController
) {
    composable<ChangeNicknameNestedRoute.MyPage> {
        MyPageScreenRoot(
            onNavigateToChangeNickname = {
                navController.navigate(MyPageNestedRoute.ChangeNickname)
            },
            onNavigateToChangePassword = {
                navController.navigate(MyPageNestedRoute.ChangePassword)
            },
            onNavigateToLogin = {
                navController.navigate(MyPageNestedRoute.Login)
            },
            onNavigateToReviewHistory = {
                navController.navigate(MyPageNestedRoute.ReviewHistory)
            }
        )
    }
}

fun NavGraphBuilder.changePasswordNavGraph(
    navController: NavHostController
) {
    composable<ChangePasswordNestedRoute.MyPage> {
        MyPageScreenRoot(
            onNavigateToChangeNickname = {
                navController.navigate(MyPageNestedRoute.ChangeNickname)
            },
            onNavigateToChangePassword = {
                navController.navigate(MyPageNestedRoute.ChangePassword)
            },
            onNavigateToLogin = {
                navController.navigate(MyPageNestedRoute.Login)
            },
            onNavigateToReviewHistory = {
                navController.navigate(MyPageNestedRoute.ReviewHistory)
            }
        )
    }
}

fun NavGraphBuilder.productDetailNavGraph(
    navController: NavHostController
) {
    composable<ProductDetailNestedRoute.ReviewWrite>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) {
        val product = it.toRoute<ProductDetailNestedRoute.ReviewWrite>().product
        ReviewWriteScreenRoot(
            product = product,
            onReviewWriteComplete = {
                navController.popBackStack()
            },
        )
    }
}

fun NavGraphBuilder.reviewEditNavGraph(
    navController: NavHostController
) {
    composable<ReviewEditNestedRoute.ReviewHistory> {
        ReviewHistoryScreenRoot(
            onNavigateToReviewEdit = { reviewInfo ->
                navController.navigate(ReviewHistoryNestedRoute.ReviewEdit(reviewInfo))
            }
        )
    }
}

fun NavGraphBuilder.reviewHistoryNavGraph(
    navController: NavHostController
) {
    composable<ReviewHistoryNestedRoute.ReviewEdit>(
        typeMap = mapOf(typeOf<ReviewInfo>() to parcelableType<ReviewInfo>())
    ) {
        val reviewInfo = it.toRoute<ReviewHistoryNestedRoute.ReviewEdit>().reviewInfo
        ReviewEditScreenRoot(
            reviewInfo = reviewInfo,
            onNavigateReviewHistory = {
                navController.navigate(ReviewEditNestedRoute.ReviewHistory)
            }
        )
    }
}

fun NavGraphBuilder.reviewWriteNavGraph(
    navController: NavHostController
) {
    composable<ReviewWriteNestedRoute.ProductDetail>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) {
        val product = it.toRoute<ReviewWriteNestedRoute.ProductDetail>().product
        ProductDetailScreenRoot(
            product = product,
            onNavigateToReviewWrite = {
                navController.navigate(ProductDetailNestedRoute.ReviewWrite(product))

            }
        )
    }
}

fun NavGraphBuilder.searchResultNavGraph(
    navController: NavHostController
) {
    composable<SearchResultNestedRoute.ProductDetail>(
        typeMap = mapOf(typeOf<Product>() to parcelableType<Product>())
    ) {
        val product = it.toRoute<SearchResultNestedRoute.ProductDetail>().product
        ProductDetailScreenRoot(
            product = product,
            onNavigateToReviewWrite = {
                navController.navigate(ProductDetailNestedRoute.ReviewWrite(product))
            }
        )
    }
}

fun NavGraphBuilder.registerSuccessNavGraph(
    navController: NavHostController
) {
    composable<RegisterNestedRoute.RegisterSuccess> {
        RegisterSuccessScreen(
            onNavigateToLogin = {
                navController.navigate(MyPageNestedRoute.Login) {
                    popUpTo(MainNavigationRoute.MyPage.route) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            }
        )
    }

    composable<RegisterSuccessNestedRoute.Login> {
        LoginScreenRoot(
            onNavigateToRegister = {
                navController.navigate(LoginNestedRoute.Register)
            },
            onNavigateToMyPage = {
                navController.navigate(ChangeNicknameNestedRoute.MyPage)
            }
        )
    }
}
