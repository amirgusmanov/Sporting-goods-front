package com.example.springboot_project_front.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.springboot_project_front.data.RetrofitClient
import com.example.springboot_project_front.data.SportingGoodsService
import com.example.springboot_project_front.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private var _state: MutableStateFlow<State> = MutableStateFlow(State.HideLoading)
    val state: StateFlow<State> = _state

    private val sportingGoodsApi = RetrofitClient.instance.create(SportingGoodsService::class.java)

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _state.value = State.ShowLoading
            val token: String? = try {
                sportingGoodsApi.register(User(email = email, password = password))
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            if (token != "Bad credentials") {
                _state.value = State.Success(token!!)
            } else {
                _state.value = State.Error("Bad credentials")
            }
            _state.value = State.HideLoading
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = State.ShowLoading
            val token: String? =
                try {
                    sportingGoodsApi.login(User(email = email, password = password))
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            if (token != "Bad credentials") {
                _state.value = State.Success(token!!)
            } else {
                _state.value = State.Error("Bad credentials")
            }
            _state.value = State.HideLoading
        }
    }

    sealed interface State {
        data object ShowLoading : State
        data object HideLoading : State
        data class Success(val token: String) : State
        data class Error(val error: String) : State
    }
}