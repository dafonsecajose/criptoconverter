package br.com.jose.criptoconverter.data.repository

import br.com.jose.criptoconverter.data.model.ExchangeResponse
import kotlinx.coroutines.flow.Flow


interface CryptoRepository {
    suspend fun getExchangeResponse(coin: String): Flow<ExchangeResponse>

    suspend fun save(exchange: ExchangeResponse)
    fun list(): Flow<List<ExchangeResponse>>
}