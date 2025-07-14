package com.hjw0623.presentation.screen.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hjw0623.core.domain.auth.UserDataValidator
import com.hjw0623.presentation.screen.auth.viewmodel.LoginViewModel

class LoginViewModelFactory(
    private val userDataValidator: UserDataValidator
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userDataValidator) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
