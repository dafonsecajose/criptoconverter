package br.com.jose.criptoconverter.data.repository

import br.com.jose.criptoconverter.core.exceptions.RemoteException
import br.com.jose.criptoconverter.data.model.ErrorResponse
import br.com.jose.criptoconverter.data.model.ExchangeResponseValue
import br.com.jose.criptoconverter.data.services.MercadoBitcoinService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CryptoRepositoryImpl(
    private val service: MercadoBitcoinService
): CryptoRepository {
    override suspend fun getExchangeValue(coin: String) = flow {
        try {
            val exchangeValue = service.exchangeValue(coin)
            val exchange = exchangeValue.values.first()
            emit(exchange)
        } catch (e: HttpException) {
            val json = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(json, ErrorResponse::class.java)
            throw RemoteException(errorResponse.message)
        }
    }

    override suspend fun save(exchange: ExchangeResponseValue) {
        TODO("Not yet implemented")
    }

    override fun list(): Flow<List<ExchangeResponseValue>> {
        TODO("Not yet implemented")
    }
}