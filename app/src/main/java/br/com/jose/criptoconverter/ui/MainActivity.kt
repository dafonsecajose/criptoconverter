package br.com.jose.criptoconverter.ui

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import br.com.jose.criptoconverter.R
import br.com.jose.criptoconverter.core.extensions.*
import br.com.jose.criptoconverter.data.model.CryptoCoin
import br.com.jose.criptoconverter.databinding.ActivityMainBinding
import br.com.jose.criptoconverter.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()
    private val dialog by lazy { createProgressDialog() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindAdapters()
        bindListeners()
        bindObserver()
    }

    private fun bindListeners() {
        with(binding) {
            edValue.doAfterTextChanged {
                btnConverter.isEnabled = it != null && it.toString().isNotEmpty()
            }

            btnConverter.setOnClickListener {
                it.hideSoftKeyboard()
                val search = tilFrom.text
                viewModel.getExchangeModel(search)
                viewModel.setConvertValue(binding.tilValue.text.toBigDecimal())
            }
        }
    }

    private fun bindAdapters() {
        val list = CryptoCoin.values()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        with(binding) {
            tvFrom.setAdapter(adapter)
            tvFrom.setText(CryptoCoin.BTC.name, false)
        }
    }

    private fun bindObserver() {
        viewModel.state.observe(this){
            when (it) {
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog{
                        setMessage(it.error.message)
                    }.show()
                }
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                }
            }
        }

        viewModel.result.observe(this){
            if (it.formatCurrency().length in 14..20){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvResult.setTextAppearance(R.style.TextAppearance_MaterialComponents_Headline4)
                }
            } else if (it.formatCurrency().length > 20){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.tvResult.setTextAppearance(R.style.TextAppearance_MaterialComponents_Headline5)
                }
            }
            binding.tvResult.text = it.formatCurrency()
        }
    }
}