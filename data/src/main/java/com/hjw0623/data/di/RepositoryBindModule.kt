package com.hjw0623.data.di

import com.hjw0623.core.business_logic.repository.AuthRepository
import com.hjw0623.core.business_logic.repository.KakaoRepository
import com.hjw0623.core.business_logic.repository.MyPageRepository
import com.hjw0623.core.business_logic.repository.ProductRepository
import com.hjw0623.core.business_logic.repository.ReviewRepository
import com.hjw0623.core.business_logic.repository.SearchRepository
import com.hjw0623.core.business_logic.repository.UserDataStoreRepository
import com.hjw0623.data.repository.AuthRepositoryImpl
import com.hjw0623.data.repository.KakaoRepositoryImpl
import com.hjw0623.data.repository.MyPageRepositoryImpl
import com.hjw0623.data.repository.ProductRepositoryImpl
import com.hjw0623.data.repository.ReviewRepositoryImpl
import com.hjw0623.data.repository.SearchRepositoryImpl
import com.hjw0623.data.repository.UserDataStoreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindModule {

    @Binds @Singleton abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
    @Binds @Singleton abstract fun bindMyPageRepository(impl: MyPageRepositoryImpl): MyPageRepository
    @Binds @Singleton abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository
    @Binds @Singleton abstract fun bindReviewRepository(impl: ReviewRepositoryImpl): ReviewRepository
    @Binds @Singleton abstract fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository
    @Binds @Singleton abstract fun bindKakaoRepository(impl: KakaoRepositoryImpl): KakaoRepository
    @Binds @Singleton abstract fun bindUserDataStoreRepository(impl: UserDataStoreRepositoryImpl): UserDataStoreRepository
}