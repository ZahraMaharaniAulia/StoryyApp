package com.zahra.submissionstoryapp.ui.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zahra.submissionstoryapp.R
import com.zahra.submissionstoryapp.adapter.LoadingStateAdapter
import com.zahra.submissionstoryapp.data.ViewModelFactory
import com.zahra.submissionstoryapp.databinding.ActivityMainBinding
import com.zahra.submissionstoryapp.ui.maps.MapsActivity
import com.zahra.submissionstoryapp.ui.view.story.AddStoryActivity
import com.zahra.submissionstoryapp.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var storyAdapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAction()

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_actionbar)

        itemDecoration()

        binding.rvUser.adapter = storyAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                storyAdapter.retry()
            })

        viewModel.getSession().observe(this) { user ->
            Log.d("token", "onCreate: ${user.token}")
            Log.d("user", "onCreate: ${user.isLogin}")
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        viewModel.story.observe(this) { story ->
            storyAdapter.submitData(lifecycle, story)
        }
    }

    private fun setUpAction() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    fun itemDecoration() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)
    }

    fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBarUser.visibility = View.VISIBLE
            } else {
                progressBarUser.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }

            R.id.menu2 -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }

            R.id.menu3 -> {
                viewModel.logout()

                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}
