package com.hjw0623.core.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "user_prefs"
val Context.userDataStore by preferencesDataStore(name = DATASTORE_NAME)

class UserPreferenceDataStore(context: Context) {

    private val appContext = context.applicationContext
    private val dataStore = appContext.userDataStore

    companion object {
        val KEY_NICKNAME = stringPreferencesKey("nickname")
        val KEY_EMAIL = stringPreferencesKey("email")
        val KEY_PASSWORD = stringPreferencesKey("password")
        val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val KEY_SEARCH_HISTORY = stringPreferencesKey("search_history")
    }

    suspend fun saveUserInfo(
        nickname: String,
        email: String,
        password: String,
        accessToken: String,
        refreshToken: String
    ) {
        dataStore.edit { preferences ->
            preferences[KEY_NICKNAME] = nickname
            preferences[KEY_EMAIL] = email
            preferences[KEY_PASSWORD] = password
            preferences[KEY_ACCESS_TOKEN] = accessToken
            preferences[KEY_REFRESH_TOKEN] = refreshToken
            preferences[KEY_IS_LOGGED_IN] = true
        }
    }

    suspend fun clearLoginInfo() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun saveSearchHistory(query: String) {
        dataStore.edit { preferences ->
            val currentHistory = preferences[KEY_SEARCH_HISTORY]?.split(",") ?: emptyList()
            val newHistory = listOf(query) + currentHistory.filterNot { it == query }
            preferences[KEY_SEARCH_HISTORY] = newHistory.joinToString(",")
        }
    }

    suspend fun removeSearchHistory(query: String) {
        dataStore.edit { preferences ->
            val currentHistory = preferences[KEY_SEARCH_HISTORY]?.split(",") ?: emptyList()
            val newHistory = currentHistory.filterNot { it == query }
            preferences[KEY_SEARCH_HISTORY] = newHistory.joinToString(",")
        }
    }

    val nicknameFlow: Flow<String?> = dataStore.data.map { it[KEY_NICKNAME] }
    val emailFlow: Flow<String?> = dataStore.data.map { it[KEY_EMAIL] }
    val passwordFlow: Flow<String?> = dataStore.data.map { it[KEY_PASSWORD] }
    val accessTokenFlow: Flow<String?> = dataStore.data.map { it[KEY_ACCESS_TOKEN] }
    val refreshTokenFlow: Flow<String?> = dataStore.data.map { it[KEY_REFRESH_TOKEN] }
    val isLoggedInFlow: Flow<Boolean> = dataStore.data.map { it[KEY_IS_LOGGED_IN] ?: false }
    val searchHistoryFlow: Flow<List<String>> = dataStore.data.map { preferences ->
        preferences[KEY_SEARCH_HISTORY]?.split(",") ?: emptyList()
    }
}