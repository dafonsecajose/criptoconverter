package br.com.jose.criptoconverter.data.model

data class ErrorResponse(
    val status: Long,
    val code: String,
    val message: String
)
