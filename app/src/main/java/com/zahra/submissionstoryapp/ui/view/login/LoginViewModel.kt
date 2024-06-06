package com.zahra.submissionstoryapp.ui.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zahra.submissionstoryapp.data.Result
import com.zahra.submissionstoryapp.data.repository.StoryRepository
import com.zahra.submissionstoryapp.data.response.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> get() = _loginResult

    fun login(email: String, password: String) {
        _loginResult.value = Result.Loading

        viewModelScope.launch {
            val result = storyRepository.login(email, password)
            _loginResult.value = result
        }
    }
}