package br.com.jose.criptoconverter.domain

import br.com.jose.criptoconverter.core.UseCase
import br.com.jose.criptoconverter.data.model.ExchangeResponseValue
import br.com.jose.criptoconverter.data.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveExchangeUseCase(
    private val repository: CryptoRepository
): UseCase.NoSource<ExchangeResponseValue>() {
    override suspend fun execute(param: ExchangeResponseValue): Flow<Unit> {
        return flow {
            repository.save(param)
            emit(Unit)
        }
    }
}