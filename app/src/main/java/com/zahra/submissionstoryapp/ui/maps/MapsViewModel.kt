package com.zahra.submissionstoryapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zahra.submissionstoryapp.data.Result
import com.zahra.submissionstoryapp.data.repository.StoryRepository
import com.zahra.submissionstoryapp.data.response.ListStoryItem

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {

    fun getStoriesWithLocation(): LiveData<Result<List<ListStoryItem>>> {
        return repository.getStoriesWithLocation()
    }
}