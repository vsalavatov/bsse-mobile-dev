package com.vadimsalavatov.mobiledev

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.vadimsalavatov.mobiledev.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import by.kirich1409.viewbindingdelegate.viewBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        val LOG_TAG = MainActivity::javaClass.name
    }

    private val viewModel: MainViewModel by viewModels()

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Log.d(LOG_TAG, "onCreate()")
//        setupRecyclerView()
//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.viewState.collect { viewState ->
//                    Log.d(LOG_TAG, "$viewState")
//                    renderViewState(viewState)
//                }
//            }
//        }
//    }
//
//    private fun renderViewState(viewState: MainViewModel.ViewState) = when (viewState) {
//        is MainViewModel.ViewState.Loading -> {
//            viewBinding.usersRecyclerView.isVisible = false
//            viewBinding.progressBar.isVisible = true
//        }
//        is MainViewModel.ViewState.Data -> {
//            viewBinding.usersRecyclerView.isVisible = true
//            (viewBinding.usersRecyclerView.adapter as UserAdapter).apply {
//                userList = viewState.users
//                notifyDataSetChanged()
//            }
//            viewBinding.progressBar.isVisible = false
//        }
//    }
//
//    private fun setupRecyclerView(): UserAdapter {
//        val usersRecyclerView = findViewById<RecyclerView>(R.id.usersRecyclerView)
//        val userAdapter = UserAdapter()
//        usersRecyclerView.adapter = userAdapter
//        return userAdapter
//    }
}