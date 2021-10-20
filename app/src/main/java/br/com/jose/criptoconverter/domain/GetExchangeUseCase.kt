package br.com.jose.criptoconverter.domain

import br.com.jose.criptoconverter.core.UseCase
import br.com.jose.criptoconverter.data.model.ExchangeResponseValue
import br.com.jose.criptoconverter.data.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow

class GetExchangeUseCase(
    private val repository: CryptoRepository
): UseCase<String, ExchangeResponseValue>() {
    override suspend fun execute(param: String): Flow<ExchangeResponseValue> {
        return repository.getExchangeValue(param)
    }

}