package com.zahra.submissionstoryapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.zahra.submissionstoryapp.data.pref.UserPreference
import com.zahra.submissionstoryapp.data.pref.dataStore
import com.zahra.submissionstoryapp.data.remote.retrofit.ApiConfig
import com.zahra.submissionstoryapp.data.repository.StoryRepository
import com.zahra.submissionstoryapp.database.StoryDataBase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val dataBase = StoryDataBase.getDatabase(context)
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return StoryRepository.getInstance(dataBase, apiService, pref)
    }
}