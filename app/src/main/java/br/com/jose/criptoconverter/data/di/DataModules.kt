package br.com.jose.criptoconverter.data.di

import android.util.Log
import br.com.jose.criptoconverter.data.repository.CryptoRepository
import br.com.jose.criptoconverter.data.repository.CryptoRepositoryImpl
import br.com.jose.criptoconverter.data.services.MercadoBitcoinService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModules {

    private const val HTTP_TAG = "OkHttp"

    fun load() {
        loadKoinModules(networkModule() + repositoryModule())
    }

    private fun networkModule(): Module {
        return module {
          single {
              val interceptor = HttpLoggingInterceptor {
                  Log.e(HTTP_TAG, ": $it")
              }
              interceptor.level = HttpLoggingInterceptor.Level.BODY

              OkHttpClient.Builder()
                  .addInterceptor(interceptor)
                  .build()
          }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }
            single {
                createService<MercadoBitcoinService>(get(), get())
            }
        }
    }

    private fun repositoryModule(): Module {
        return module {
            single<CryptoRepository> { CryptoRepositoryImpl(get()) }
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory): T {
        return Retrofit.Builder()
            .baseUrl("https://www.mercadobitcoin.net/api/")
            .client(client)
            .addConverterFactory(factory)
            .build()
            .create(T::class.java)
    }

}