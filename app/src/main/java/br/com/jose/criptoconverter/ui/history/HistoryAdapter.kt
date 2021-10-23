package br.com.jose.criptoconverter.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.jose.criptoconverter.R
import br.com.jose.criptoconverter.core.extensions.formatCurrency
import br.com.jose.criptoconverter.core.extensions.formatDate
import br.com.jose.criptoconverter.data.model.CryptoCoin
import br.com.jose.criptoconverter.data.model.ExchangeResponseValue
import br.com.jose.criptoconverter.databinding.ItemHistoryBinding

class HistoryAdapter: ListAdapter<ExchangeResponseValue, HistoryAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(
        private val binding: ItemHistoryBinding
    ): RecyclerView.ViewHolder(binding.root) {

        var button = binding.btnMore
        fun bind(item: ExchangeResponseValue) {
            binding.tvName.text = CryptoCoin.getByName(item.codeIn)?.description
            val result = item.last.toBigDecimal() * item.valueBrl.toBigDecimal()
            binding.tvValue.text = result.formatCurrency()
            binding.tvValueUnit.text = item.valueBrl.toBigDecimal().formatCurrency()
            binding.tvDate.text = item.date.formatDate("dd/MM/yyyy")
            binding.card.visibility = if(item.shouldBeExpanded) View.VISIBLE else View.GONE
            binding.btnMore.setBackgroundResource(if(item.shouldBeExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.button.setOnClickListener {
            onCollapseCardView(position)
        }
        holder.bind(getItem(position))
    }

    private fun onCollapseCardView(position: Int){
        val list = currentList
        for (i in 0 until list.size) {
            list[i].shouldBeExpanded = i == position
        }
       //notifyDataSetChanged()
    }
}

class DiffCallback: DiffUtil.ItemCallback<ExchangeResponseValue>() {
    override fun areItemsTheSame(
        oldItem: ExchangeResponseValue,
        newItem: ExchangeResponseValue
    ) = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: ExchangeResponseValue,
        newItem: ExchangeResponseValue
    ) = oldItem.codeIn == newItem.codeIn && oldItem.date == newItem.date && oldItem.last == newItem.last &&
            oldItem.valueBrl == newItem.valueBrl

}