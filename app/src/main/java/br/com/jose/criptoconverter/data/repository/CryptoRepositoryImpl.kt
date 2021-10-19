package br.com.jose.criptoconverter.data.repository

import br.com.jose.criptoconverter.core.exceptions.RemoteException
import br.com.jose.criptoconverter.data.model.ErrorResponse
import br.com.jose.criptoconverter.data.model.ExchangeResponse
import br.com.jose.criptoconverter.data.services.AwesomeService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CryptoRepositoryImpl(
    private val service: AwesomeService
): CryptoRepository {
    override suspend fun getExchangeResponse(coin: String) = flow {
        try {
            val exchange = service.exchangeValue(coin)
            emit(exchange)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(json, ErrorResponse::class.java)
            throw RemoteException(errorResponse.message)
        }
    }

    override suspend fun save(exchange: ExchangeResponse) {
        TODO("Not yet implemented")
    }

    override fun list(): Flow<List<ExchangeResponse>> {
        TODO("Not yet implemented")
    }
}