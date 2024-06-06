package com.zahra.submissionstoryapp.ui.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zahra.submissionstoryapp.data.Result
import com.zahra.submissionstoryapp.data.repository.StoryRepository
import com.zahra.submissionstoryapp.data.response.RegisterResponse
import kotlinx.coroutines.launch

class RegisterViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    private val _registrationResult = MutableLiveData<Result<RegisterResponse>>()
    val registrationResult: LiveData<Result<RegisterResponse>> get() = _registrationResult

    fun register(name: String, email: String, password: String) {
        _registrationResult.value = Result.Loading

        viewModelScope.launch {
            val result = storyRepository.register(name, email, password)
            _registrationResult.value = result
        }
    }
}