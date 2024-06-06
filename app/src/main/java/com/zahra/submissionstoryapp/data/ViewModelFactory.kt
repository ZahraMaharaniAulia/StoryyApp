package com.zahra.submissionstoryapp.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zahra.submissionstoryapp.di.Injection
import com.zahra.submissionstoryapp.data.repository.StoryRepository
import com.zahra.submissionstoryapp.ui.maps.MapsViewModel
import com.zahra.submissionstoryapp.ui.view.login.LoginViewModel
import com.zahra.submissionstoryapp.ui.view.main.MainViewModel
import com.zahra.submissionstoryapp.ui.view.register.RegisterViewModel
import com.zahra.submissionstoryapp.ui.view.story.AddStoryViewModel

class ViewModelFactory(private val repository: StoryRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                AddStoryViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return ViewModelFactory(Injection.provideRepository(context))
        }
    }
}