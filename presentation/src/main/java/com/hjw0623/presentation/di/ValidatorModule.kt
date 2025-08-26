package com.hjw0623.presentation.di

import com.hjw0623.core.business_logic.auth.validator.EmailPatternValidator
import com.hjw0623.core.business_logic.auth.validator.UserDataValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ValidatorModule {

    @Singleton
    @Provides
    fun provideEmailPatternValidator(): EmailPatternValidator = EmailPatternValidator

    @Singleton
    @Provides
    fun provideUserDataValidator(
        emailPatternValidator: EmailPatternValidator
    ): UserDataValidator = UserDataValidator(emailPatternValidator)
}