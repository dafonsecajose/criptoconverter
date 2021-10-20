package br.com.jose.criptoconverter.data.repository

import br.com.jose.criptoconverter.data.model.ExchangeResponseValue
import kotlinx.coroutines.flow.Flow


interface CryptoRepository {
    suspend fun getExchangeValue(coin: String): Flow<ExchangeResponseValue>

    suspend fun save(exchange: ExchangeResponseValue)
    fun list(): Flow<List<ExchangeResponseValue>>
}