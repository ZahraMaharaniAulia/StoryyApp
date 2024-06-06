package com.zahra.submissionstoryapp.ui.view.story

import androidx.lifecycle.ViewModel
import com.zahra.submissionstoryapp.data.repository.StoryRepository
import java.io.File

class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {

    fun uploadStories(file: File, description: String, lat: Double? = null, lon: Double? = null) =
        repository.uploadStories(file, description, lat, lon)
}