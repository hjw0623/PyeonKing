package com.hjw0623.pyeonking.navigation.nav_route

import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.SearchResultNavArgs
import com.hjw0623.pyeonking.review.review_history.data.ReviewInfo
import kotlinx.serialization.Serializable

sealed interface MainNavigationRoute {
    val route: String

    object Home : MainNavigationRoute {
        override val route = "home"
    }

    object Camera : MainNavigationRoute {
        override val route = "camera_search"
    }

    object TextSearch : MainNavigationRoute {
        override val route = "text_search"
    }

    object MyPage : MainNavigationRoute {
        override val route = "mypage"
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