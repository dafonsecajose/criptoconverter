package br.com.jose.criptoconverter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

typealias ExchangeResponse = HashMap<String, ExchangeResponseValue>

@Entity(tableName = "tb_exchange")
data class ExchangeResponseValue(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val last: Double,
    val date: Long,
    var valueBrl: Double,
    var codeIn: String,
    var shouldBeExpanded: Boolean = false,
)