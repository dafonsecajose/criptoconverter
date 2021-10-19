package br.com.jose.criptoconverter

import android.app.Application
import br.com.jose.criptoconverter.data.di.DataModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
        }

        DataModules.load()
    }
}