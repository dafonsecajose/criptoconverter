package br.com.jose.criptoconverter.data.model

enum class CryptoCoin(val description: String) {
    ADA("CARDANO"),
    BTC("BITCOIN BTC"),
    ETH("ETHERIUM"),
    LTC("LITECOIN");

   companion object {
       fun getByName(name: String) = values().find { it.name == name }
   }
}