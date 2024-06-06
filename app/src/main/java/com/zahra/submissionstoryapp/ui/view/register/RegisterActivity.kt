package com.zahra.submissionstoryapp.ui.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.zahra.submissionstoryapp.R
import com.zahra.submissionstoryapp.data.Result
import com.zahra.submissionstoryapp.data.ViewModelFactory
import com.zahra.submissionstoryapp.data.response.RegisterResponse
import com.zahra.submissionstoryapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()
        setupAction()

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_actionbar)
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 2000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val register =
            ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val name = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val etlName =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val email = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val etlEmail =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val password =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val etlPassword =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(title, name, etlName, email, etlEmail, password, etlPassword, register)
            start()
        }
    }

    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.register(name, email, password)
            viewModel.registrationResult.observe(this) { result ->
                when (result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        val response: RegisterResponse = result.data
                        AlertDialog.Builder(this).apply {
                            setTitle("Yeah!")
                            setMessage(response.message)
                            setPositiveButton("Lanjut") { _, _ ->
                                finish()
                            }
                            create()
                            show()
                        }
                    }

                    is Result.Error -> {
                        showLoading(false)
                        val errorMessage: String = result.error
                        AlertDialog.Builder(this).apply {
                            setTitle("OOPS")
                            setMessage(errorMessage)
                            setPositiveButton("OKE") { _, _ ->
                            }
                            create()
                            show()
                        }
                    }
                }
            }
        }
    }

    fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }
}