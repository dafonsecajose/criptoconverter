package br.com.jose.criptoconverter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jose.criptoconverter.data.model.ExchangeResponseValue
import br.com.jose.criptoconverter.domain.GetExchangeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.math.BigDecimal

class MainViewModel(
    private val getExchangeUseCase: GetExchangeUseCase
): ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val _converterValue = MutableLiveData<BigDecimal>()
    private val _result = MutableLiveData<BigDecimal>()
    val result: LiveData<BigDecimal> = _result

    fun getExchangeModel(coin: String){
        viewModelScope.launch {
            getExchangeUseCase(coin)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    converterCrypto(it)
                    _state.value = State.Success(it)
                }
        }
    }

    fun setConvertValue(value: BigDecimal) {
        _converterValue.value = value
    }

    private fun converterCrypto(exchange: ExchangeResponseValue){
        _result.value = exchange.last * _converterValue.value!!

    }

    sealed class State {
        object Loading: State()

        data class Success(val exchange: ExchangeResponseValue): State()
        data class Error(val error: Throwable): State()
    }
}