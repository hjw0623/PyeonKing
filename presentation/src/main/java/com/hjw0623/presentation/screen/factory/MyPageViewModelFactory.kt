package com.hjw0623.presentation.screen.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hjw0623.presentation.screen.mypage.viewmodel.MyPageViewModel

class MyPageViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyPageViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}