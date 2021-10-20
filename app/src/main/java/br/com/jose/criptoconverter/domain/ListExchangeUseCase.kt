package br.com.jose.criptoconverter.domain

import br.com.jose.criptoconverter.core.UseCase
import br.com.jose.criptoconverter.data.model.ExchangeResponseValue
import br.com.jose.criptoconverter.data.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow

class ListExchangeUseCase(
    private val repository: CryptoRepository
): UseCase.NoParam<List<ExchangeResponseValue>>() {
    override suspend fun execute(): Flow<List<ExchangeResponseValue>> {
        return repository.list()
    }
}