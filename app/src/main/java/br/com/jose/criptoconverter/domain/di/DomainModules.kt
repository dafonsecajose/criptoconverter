package br.com.jose.criptoconverter.domain.di

import br.com.jose.criptoconverter.domain.GetExchangeUseCase
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
        }
    }
}