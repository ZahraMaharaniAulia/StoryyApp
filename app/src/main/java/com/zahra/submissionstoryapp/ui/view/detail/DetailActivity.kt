package com.zahra.submissionstoryapp.ui.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import com.zahra.submissionstoryapp.data.ViewModelFactory
import com.bumptech.glide.Glide
import com.zahra.submissionstoryapp.R
import com.zahra.submissionstoryapp.databinding.ActivityDetailBinding
import com.zahra.submissionstoryapp.ui.view.main.MainViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra(ID) ?: ""
        name = intent.getStringExtra(NAME) ?: ""
        description = intent.getStringExtra(DESCRIPTION) ?: ""
        picture = intent.getStringExtra(PICTURE) ?: ""

        binding.tvDetailName.text = name
        binding.tvDetailDescription.text = description

        Glide.with(this).load(picture).into(binding.ivDetailPhoto)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_actionbar)
    }

    companion object {
        const val ID = "ID"
        const val NAME = "NAME"
        const val DESCRIPTION = "DESCRIPTION"
        const val PICTURE = "PICTURE"

        var id: String = ""
        var name: String = ""
        var description: String? = null
        var picture: String? = null
    }
}