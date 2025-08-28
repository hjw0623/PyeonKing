package com.hjw0623.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.hjw0623.core.core_domain.repository.UserDataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserDataStoreRepository {

    companion object {
        val KEY_NICKNAME = stringPreferencesKey("nickname")
        val KEY_EMAIL = stringPreferencesKey("email")
        val KEY_PASSWORD = stringPreferencesKey("password")
        val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val KEY_SEARCH_HISTORY = stringPreferencesKey("search_history")
    }

    override suspend fun saveUserInfo(
        nickname: String,
        email: String,
        accessToken: String,
        refreshToken: String
    ) {
        dataStore.edit { preferences ->
            preferences[KEY_NICKNAME] = nickname
            preferences[KEY_EMAIL] = email
            preferences[KEY_ACCESS_TOKEN] = accessToken
            preferences[KEY_REFRESH_TOKEN] = refreshToken
            preferences[KEY_IS_LOGGED_IN] = true
        }
    }

    override suspend fun clearLoginInfo() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override suspend fun saveSearchHistory(query: String) {
        dataStore.edit { preferences ->
            val current = preferences[KEY_SEARCH_HISTORY]?.split(",")?.filter { it.isNotBlank() }
                ?: emptyList()
            val newHistory = listOf(query.trim()) + current.filterNot {
                it.equals(
                    query.trim(),
                    ignoreCase = true
                )
            }
            preferences[KEY_SEARCH_HISTORY] = newHistory.joinToString(",")
        }
    }

    override suspend fun removeSearchHistory(query: String) {
        dataStore.edit { preferences ->
            val current = preferences[KEY_SEARCH_HISTORY]?.split(",")?.filter { it.isNotBlank() }
                ?: emptyList()
            val newHistory = current.filterNot { it.equals(query.trim(), ignoreCase = true) }
            preferences[KEY_SEARCH_HISTORY] = newHistory.joinToString(",")
        }
    }

    override val nicknameFlow: Flow<String?> = dataStore.data.map { it[KEY_NICKNAME] }
    override val emailFlow: Flow<String?> = dataStore.data.map { it[KEY_EMAIL] }
    override val passwordFlow: Flow<String?> = dataStore.data.map { it[KEY_PASSWORD] }
    override val accessTokenFlow: Flow<String?> = dataStore.data.map { it[KEY_ACCESS_TOKEN] }
    override val refreshTokenFlow: Flow<String?> = dataStore.data.map { it[KEY_REFRESH_TOKEN] }
    override val isLoggedInFlow: Flow<Boolean> =
        dataStore.data.map { it[KEY_IS_LOGGED_IN] ?: false }
    override val searchHistoryFlow: Flow<List<String>> = dataStore.data.map { prefs ->
        prefs[KEY_SEARCH_HISTORY]
            ?.split(",")
            ?.filter { it.isNotBlank() }
            ?: emptyList()
    }
}