package com.example.springboot_project_front.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.springboot_project_front.data.ProductItem
import com.example.springboot_project_front.data.RetrofitClient
import com.example.springboot_project_front.data.SportingGoodsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SportingGoodsViewModel : ViewModel() {

    private var _state: MutableStateFlow<State> = MutableStateFlow(State.HideLoading)
    val state: StateFlow<State> = _state

    private val sportingGoodsApi = RetrofitClient.instance.create(SportingGoodsService::class.java)

    fun getAllSportingGoods() {
        viewModelScope.launch {
            _state.value = State.ShowLoading
            delay(2000)
            val result = withContext(Dispatchers.IO) { sportingGoodsApi.getAllSportingGoods() }
            _state.value = State.Products(result)
            _state.value = State.HideLoading
        }
    }

    fun deleteElement(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sportingGoodsApi.deleteSportingGood(id)
            }
        }
    }

    fun updateElement(id: Long, count: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = sportingGoodsApi.updateSportingGood(id, count)
                _state.value = State.Success(result)
            }
        }
    }

    fun createElement(name: String?, description: String?, count: Int?, price: Double?) {
        if (name == null || description == null || count == null || price == null) return
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = sportingGoodsApi.addSportingGood(
                    ProductItem(0, name, price, description, count).mapTo()
                )
                _state.value = State.Success(result)
            }
        }
    }

    sealed interface State {
        data object ShowLoading : State
        data object HideLoading : State
        data class Products(val list: List<ProductItem>) : State
        data class Success(val success: Boolean) : State
    }
}