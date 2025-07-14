package com.hjw0623.pyeonking.navigation.nav_route

import com.hjw0623.core.util.constants.ScreenRoutes
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.search.search_result.SearchResultNavArgs
import com.hjw0623.core.domain.review.review_history.ReviewInfo
import kotlinx.serialization.Serializable

sealed interface MainNavigationRoute {
    val route: String

    object Home : MainNavigationRoute {
        override val route = ScreenRoutes.HOME
    }

    object Camera : MainNavigationRoute {
        override val route = ScreenRoutes.CAMERA_SEARCH
    }

    object TextSearch : MainNavigationRoute {
        override val route = ScreenRoutes.TEXT_SEARCH
    }

    object MyPage : MainNavigationRoute {
        override val route = ScreenRoutes.MYPAGE
    }
}

@Serializable
sealed interface HomeTabNestedRoute {
    @Serializable
    data class ProductDetail(val product: Product) : HomeTabNestedRoute

    @Serializable
    data class SearchResult(val searchResultNavArgs: SearchResultNavArgs) : HomeTabNestedRoute

    @Serializable
    data class ReviewWrite(val product: Product) : HomeTabNestedRoute
}

@Serializable
sealed interface CameraTabNestedRoute {
    @Serializable
    data class SearchResult(val searchResultNavArgs: SearchResultNavArgs) : CameraTabNestedRoute

    @Serializable
    data class ProductDetail(val product: Product) : CameraTabNestedRoute

    @Serializable
    data class ReviewWrite(val product: Product) : CameraTabNestedRoute
}


@Serializable
sealed interface TextSearchTabNestedRoute {
    @Serializable
    data class SearchResult(val searchResultNavArgs: SearchResultNavArgs) : TextSearchTabNestedRoute

    @Serializable
    data class ProductDetail(val product: Product) : TextSearchTabNestedRoute

    @Serializable
    data class ReviewWrite(val product: Product) : TextSearchTabNestedRoute
}

@Serializable
sealed interface MyPageTabNestedRoute {
    @Serializable
    data object MyPage : MyPageTabNestedRoute

    @Serializable
    data object Login : MyPageTabNestedRoute

    @Serializable
    data object Register : MyPageTabNestedRoute

    @Serializable
    data object RegisterSuccess : MyPageTabNestedRoute

    @Serializable
    data object ChangeNickname : MyPageTabNestedRoute

    @Serializable
    data object ChangePassword : MyPageTabNestedRoute

    @Serializable
    data object ReviewHistory : MyPageTabNestedRoute

    @Serializable
    data class ReviewEdit(val reviewInfo: ReviewInfo) : MyPageTabNestedRoute
}