package com.hjw0623.presentation.screen.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hjw0623.core.business_logic.auth.validator.EmailPatternValidator
import com.hjw0623.core.business_logic.auth.validator.UserDataValidator
import com.hjw0623.data.repository.AuthRepositoryImpl
import com.hjw0623.data.repository.MyPageRepositoryImpl
import com.hjw0623.data.repository.ProductRepositoryImpl
import com.hjw0623.data.repository.ReviewRepositoryImpl
import com.hjw0623.data.repository.SearchRepositoryImpl
import com.hjw0623.data.repository.UserDataStoreRepositoryImpl
import com.hjw0623.presentation.screen.auth.viewmodel.LoginViewModel
import com.hjw0623.presentation.screen.auth.viewmodel.RegisterViewModel
import com.hjw0623.presentation.screen.home.viewmodel.HomeViewModel
import com.hjw0623.presentation.screen.mypage.viewmodel.MyPageViewModel
import com.hjw0623.presentation.screen.product.viewmodel.ProductViewModel
import com.hjw0623.presentation.screen.review.viewmodel.ReviewEditViewModel
import com.hjw0623.presentation.screen.review.viewmodel.ReviewHistoryViewModel
import com.hjw0623.presentation.screen.review.viewmodel.ReviewWriteViewModel
import com.hjw0623.presentation.screen.search.viewmodel.CameraSearchViewModel
import com.hjw0623.presentation.screen.search.viewmodel.SearchResultViewModel
import com.hjw0623.presentation.screen.search.viewmodel.TextSearchViewModel

class PyeonKingViewModelFactory(
    private val appContext: Context
): ViewModelProvider.Factory  {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TextSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TextSearchViewModel(
                SearchRepositoryImpl(),
                UserDataStoreRepositoryImpl(appContext)
            ) as T
        }
        if (modelClass.isAssignableFrom(SearchResultViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchResultViewModel(SearchRepositoryImpl()) as T
        }
        if (modelClass.isAssignableFrom(ReviewWriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewWriteViewModel(ReviewRepositoryImpl()) as T
        }
        if (modelClass.isAssignableFrom(ReviewHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewHistoryViewModel(ReviewRepositoryImpl()) as T
        }
        if (modelClass.isAssignableFrom(ReviewEditViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewEditViewModel(ReviewRepositoryImpl()) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(AuthRepositoryImpl(), UserDataValidator(EmailPatternValidator)) as T
        }
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(ProductRepositoryImpl()) as T
        }
        if (modelClass.isAssignableFrom(MyPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyPageViewModel(
                MyPageRepositoryImpl(),
                UserDataValidator(EmailPatternValidator),
                UserDataStoreRepositoryImpl(appContext.applicationContext)
            ) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(
                AuthRepositoryImpl(),
                UserDataValidator(EmailPatternValidator),
                UserDataStoreRepositoryImpl(appContext.applicationContext)
            ) as T
        }
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(
                ProductRepositoryImpl(),
                UserDataStoreRepositoryImpl(appContext.applicationContext)
            ) as T
        }
        if (modelClass.isAssignableFrom(CameraSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CameraSearchViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}