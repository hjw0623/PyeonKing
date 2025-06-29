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
        override val route = "camera"
    }

    object TextSearch : MainNavigationRoute {
        override val route = "text_search"
    }

    object MyPage : MainNavigationRoute {
        override val route = "mypage"
    }
}

@Serializable
sealed interface HomeNestedRoute {
    @Serializable
    data class ProductDetail(val product: Product) : HomeNestedRoute

    @Serializable
    data class SearchResult(val searchResultNavArgs: SearchResultNavArgs) : HomeNestedRoute
}

@Serializable
sealed interface CameraNestedRoute {
    @Serializable
    data class SearchResult(val searchResultNavArgs: SearchResultNavArgs) : CameraNestedRoute
}

@Serializable
sealed interface TextSearchNestedRoute {
    @Serializable
    data class SearchResult(val searchResultNavArgs: SearchResultNavArgs) : TextSearchNestedRoute

    @Serializable
    data class ProductDetail(val product: Product) : TextSearchNestedRoute
}

@Serializable
sealed interface MyPageNestedRoute {
    @Serializable
    data object ChangeNickname : MyPageNestedRoute

    @Serializable
    data object ChangePassword : MyPageNestedRoute

    @Serializable
    data object ReviewHistory : MyPageNestedRoute

    @Serializable
    data object Login : MyPageNestedRoute
}

@Serializable
sealed interface LoginNestedRoute {
    @Serializable
    data object Register : LoginNestedRoute

    @Serializable
    data object MyPage : LoginNestedRoute
}

@Serializable
sealed interface RegisterNestedRoute {
    @Serializable
    data object Register : RegisterNestedRoute

    @Serializable
    data object RegisterSuccess : RegisterNestedRoute
}

@Serializable
sealed interface ChangeNicknameNestedRoute {
    @Serializable
    data object MyPage : ChangeNicknameNestedRoute
}

@Serializable
sealed interface ChangePasswordNestedRoute {
    @Serializable
    data object MyPage : ChangePasswordNestedRoute
}

@Serializable
sealed interface ProductDetailNestedRoute {
    @Serializable
    data class ReviewWrite(val product: Product) : ProductDetailNestedRoute
}

@Serializable
sealed interface ReviewEditNestedRoute {
    @Serializable
    data object ReviewHistory : ReviewEditNestedRoute
}

@Serializable
sealed interface ReviewHistoryNestedRoute {
    @Serializable
    data class ReviewEdit(val reviewInfo: ReviewInfo) : ReviewHistoryNestedRoute
}

@Serializable
sealed interface ReviewWriteNestedRoute {
    @Serializable
    data class ProductDetail(val product: Product) : ReviewWriteNestedRoute
}

@Serializable
sealed interface SearchResultNestedRoute {
    @Serializable
    data class ProductDetail(val product: Product) : SearchResultNestedRoute
}

@Serializable
sealed interface RegisterSuccessNestedRoute {
    @Serializable
    data object Login : RegisterSuccessNestedRoute
}