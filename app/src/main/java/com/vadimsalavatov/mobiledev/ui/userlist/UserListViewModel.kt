package com.vadimsalavatov.mobiledev.ui.userlist

import androidx.lifecycle.viewModelScope
import com.vadimsalavatov.mobiledev.data.network.Api
import com.vadimsalavatov.mobiledev.entity.User
import com.vadimsalavatov.mobiledev.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val apiLazy: dagger.Lazy<Api>) :
    BaseViewModel() {
    private val api by lazy { apiLazy.get() }

    sealed class ViewState {
        object Loading : ViewState()
        data class Data(val users: List<User>) : ViewState()
    }

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: Flow<ViewState>
        get() = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            _viewState.emit(ViewState.Loading)
            val users = loadUsers()
            _viewState.emit(ViewState.Data(users))
        }
    }

    private suspend fun loadUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            Thread.sleep(1500)
            api.getUsers().data
        }
    }
}