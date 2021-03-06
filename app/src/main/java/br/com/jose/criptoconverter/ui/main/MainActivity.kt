package br.com.jose.criptoconverter.ui.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import br.com.jose.criptoconverter.R
import br.com.jose.criptoconverter.core.extensions.*
import br.com.jose.criptoconverter.data.model.CryptoCoin
import br.com.jose.criptoconverter.databinding.ActivityMainBinding
import br.com.jose.criptoconverter.presentation.MainViewModel
import br.com.jose.criptoconverter.ui.history.HistoryActivity
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

        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_history){
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
    private fun bindListeners() {
        with(binding) {
            edValue.doAfterTextChanged {
                btnConverter.isEnabled = it != null && it.toString().isNotEmpty()
                btnSave.isEnabled = false
            }

            btnConverter.setOnClickListener {
                it.hideSoftKeyboard()
                val search = tilFrom.text
                viewModel.getExchangeModel(search)
                viewModel.setConvertValue(binding.tilValue.text.toBigDecimal())
            }

            btnSave.setOnClickListener {
                it.hideSoftKeyboard()
                viewModel.saveExchange()
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
                MainViewModel.State.Saved -> {
                    dialog.dismiss()
                    Toast.makeText(this, "Convers??o salva com sucesso", Toast.LENGTH_LONG).show()
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
            if (it != null){
                binding.btnSave.isEnabled = true
            }
        }
    }
}