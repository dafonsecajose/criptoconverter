package br.com.jose.criptoconverter.data.services

import br.com.jose.criptoconverter.data.model.ExchangeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AwesomeService {
    @GET("json/last/{coin}-BRL")
    suspend fun exchangeValue(@Path("coin") coin: String): ExchangeResponse
}