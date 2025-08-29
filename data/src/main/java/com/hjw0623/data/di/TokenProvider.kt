package com.hjw0623.data.di

import com.hjw0623.core.core_domain.repository.UserDataStoreRepository
import com.hjw0623.core.android.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenProvider @Inject constructor(
    userDataStore: UserDataStoreRepository,
    @ApplicationScope private val applicationScope: CoroutineScope
) {
    val tokenFlow: StateFlow<String?> =
        userDataStore.accessTokenFlow.stateIn(
            scope = applicationScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    fun getToken(): String? = tokenFlow.value
}

/*
    @Singleton
    class TokenProvider @Inject constructor(
        private val userDataStore: UserDataStoreRepository
    ) {
        @Volatile private var cachedToken: String? = null

        init {
            CoroutineScope(Dispatchers.IO).launch {
                userDataStore.accessTokenFlow.collectLatest { token ->
                    cachedToken = token
                }
            }
        }

        fun getToken(): String? = cachedToken
    }
 */
