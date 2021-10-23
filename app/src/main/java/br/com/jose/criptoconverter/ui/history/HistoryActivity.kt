package br.com.jose.criptoconverter.ui.history

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jose.criptoconverter.core.extensions.createDialog
import br.com.jose.criptoconverter.core.extensions.createProgressDialog
import br.com.jose.criptoconverter.databinding.ActivityHistoryBinding
import br.com.jose.criptoconverter.presentation.HistoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : AppCompatActivity() {

    private val adapter by lazy { HistoryAdapter() }
    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<HistoryViewModel>()
    private val binding by lazy { ActivityHistoryBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        lifecycle.addObserver(viewModel)
        bindingObserver()
        setupRecyclerView()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindingObserver() {
        viewModel.state.observe(this) {
            when (it) {
                is HistoryViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog { setMessage(it.error.message) }.show()
                }
                HistoryViewModel.State.Loading -> dialog.show()
                is HistoryViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.list)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding) {
            rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
            rvHistory.adapter = adapter
            rvHistory.addItemDecoration(DividerItemDecoration(this@HistoryActivity, DividerItemDecoration.HORIZONTAL))
        }
    }
}