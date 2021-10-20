package br.com.jose.criptoconverter.data.services

import br.com.jose.criptoconverter.data.model.ExchangeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MercadoBitcoinService {
    @GET("{coin}/ticker/")
    suspend fun exchangeValue(@Path("coin") coin: String): ExchangeResponse
}