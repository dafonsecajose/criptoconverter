package br.com.jose.criptoconverter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_exchange")
data class ExchangeResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val code: String,
    val codein: String,
    val name: String,
    val bid: Double
)