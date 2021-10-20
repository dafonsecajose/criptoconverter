package br.com.jose.criptoconverter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

typealias ExchangeResponse = HashMap<String, ExchangeResponseValue>

@Entity(tableName = "tb_exchange")
data class ExchangeResponseValue(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val last: BigDecimal,
)