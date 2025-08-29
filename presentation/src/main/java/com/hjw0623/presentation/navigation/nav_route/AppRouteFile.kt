package com.hjw0623.presentation.navigation.nav_route

import com.hjw0623.core.domain.model.product.Product
import com.hjw0623.core.domain.model.search.search_result.SearchResultNavArgs
import com.hjw0623.core.domain.model.review.ReviewInfo
import kotlinx.serialization.Serializable

@Serializable
sealed interface MainNavigationRoute {
    @Serializable
    data object Home : MainNavigationRoute
    @Serializable
    data object Camera : MainNavigationRoute
    @Serializable
    data object TextSearch : MainNavigationRoute
    @Serializable
    data object MyPage : MainNavigationRoute
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