package com.samsdk.mvvmexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.samsdk.mvvmexample.adapter.UserAdapter
import com.samsdk.mvvmexample.database.UserDatabase
import com.samsdk.mvvmexample.databinding.ActivityMainBinding
import com.samsdk.mvvmexample.network.ApiClient
import com.samsdk.mvvmexample.repository.UserRepository
import com.samsdk.mvvmexample.utils.NetworkHelper
import com.samsdk.mvvmexample.utils.Status
import com.samsdk.mvvmexample.viewmodel.UserViewModel
import com.samsdk.mvvmexample.viewmodel.UserViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        val networkHelper = NetworkHelper(this)
        val userRepository = UserRepository(ApiClient.apiService, UserDatabase.getInstance(this))
        viewModel = ViewModelProvider(
            this,
            UserViewModelFactory(userRepository, networkHelper)
        )[UserViewModel::class.java]
        userAdapter = UserAdapter()
        setupRv()

        viewModel.response.observe(this) { list ->
            when (list.status) {
                Status.LOADING -> {
                    binding.recyclerView.isVisible = false
                    binding.progress.isVisible = true
                }
                Status.ERROR -> {
                    binding.progress.isVisible = false
                    Toast.makeText(this, list.message, Toast.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    binding.progress.isVisible = false
                    binding.recyclerView.isVisible = true
                    userAdapter.submitList(list.data)
                }
            }
        }
    }

    private fun setupRv() = binding.recyclerView.apply {
        val divider = DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL)
        addItemDecoration(divider)
        layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = userAdapter
    }
}