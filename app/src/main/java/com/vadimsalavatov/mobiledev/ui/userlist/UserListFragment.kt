package com.vadimsalavatov.mobiledev.ui.userlist

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.vadimsalavatov.mobiledev.databinding.FragmentUserListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.fragment.app.viewModels
import com.vadimsalavatov.mobiledev.ui.base.BaseFragment
import com.vadimsalavatov.mobiledev.R

class UserListFragment : BaseFragment(R.layout.fragment_user_list) {

    private val viewModel: UserListViewModel by viewModels()
    private val viewBinding by viewBinding(FragmentUserListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribeToViewState()
    }

    private fun subscribeToViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState -> renderViewState(viewState) }
            }
        }
    }

    private fun renderViewState(viewState: UserListViewModel.ViewState) = when (viewState) {
        is UserListViewModel.ViewState.Loading -> {
            viewBinding.usersRecyclerView.isVisible = false
            viewBinding.progressBar.isVisible = true
        }
        is UserListViewModel.ViewState.Data -> {
            viewBinding.usersRecyclerView.isVisible = true
            (viewBinding.usersRecyclerView.adapter as UserAdapter).apply {
                userList = viewState.users
                notifyDataSetChanged()
            }
            viewBinding.progressBar.isVisible = false
        }
    }

    private fun setupRecyclerView() = UserAdapter().also {
        viewBinding.usersRecyclerView.adapter = it
    }
}