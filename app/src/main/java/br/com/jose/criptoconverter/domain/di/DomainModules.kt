package br.com.jose.criptoconverter.domain.di

import br.com.jose.criptoconverter.domain.GetExchangeUseCase
import br.com.jose.criptoconverter.domain.ListExchangeUseCase
import br.com.jose.criptoconverter.domain.SaveExchangeUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModules {
    fun load() {
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module {
        return module {
            factory { GetExchangeUseCase(get()) }
            factory { SaveExchangeUseCase(get()) }
            factory { ListExchangeUseCase(get()) }
        }
    }
}