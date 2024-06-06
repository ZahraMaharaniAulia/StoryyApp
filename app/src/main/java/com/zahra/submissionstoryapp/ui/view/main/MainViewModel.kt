package com.zahra.submissionstoryapp.ui.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zahra.submissionstoryapp.data.pref.UserModel
import com.zahra.submissionstoryapp.data.repository.StoryRepository
import com.zahra.submissionstoryapp.data.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: StoryRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    val story: LiveData<PagingData<ListStoryItem>> = repository.getStories().cachedIn(viewModelScope)
}