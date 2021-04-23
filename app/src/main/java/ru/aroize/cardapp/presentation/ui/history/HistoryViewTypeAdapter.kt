package ru.aroize.cardapp.presentation.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import ru.aroize.cardapp.R
import ru.aroize.cardapp.delegate.ViewTypeAdapterDelegate
import ru.aroize.cardapp.delegate.ViewTypeHolder
import ru.aroize.cardapp.delegate.ViewTypeItem
import ru.aroize.cardapp.domain.dto.CreditCard
import ru.aroize.cardapp.domain.dto.Currency
import ru.aroize.cardapp.domain.dto.Transaction
import ru.aroize.cardapp.presentation.AppContextHolder
import ru.aroize.cardapp.presentation.toActivity

@Suppress("UNCHECKED_CAST")
class HistoryViewTypeAdapter : ViewTypeAdapterDelegate.ViewTypeAdapter {
    override fun isForViewType(item: ViewTypeItem): Boolean {
        return item is HistoryItem
    }

    override fun createViewHolder(parent: ViewGroup): ViewTypeHolder<ViewTypeItem> {
        return HistoryViewHolder(parent) as ViewTypeHolder<ViewTypeItem>
    }

    class HistoryViewHolder(parent: ViewGroup) : ViewTypeHolder<HistoryItem>(
        LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
    ) {

        private val historyList = itemView.findViewById<RecyclerView>(R.id.history_list)
        private val historyListAdapter = object : RecyclerView.Adapter<HistoryTransactionViewHolder>() {

            var currency = Currency(null, "", 1f)
            var transactions = emptyList<Transaction>()

            override fun getItemCount() = transactions.size
            override fun onBindViewHolder(holder: HistoryTransactionViewHolder, position: Int) {
                holder.bind(transactions[position], currency)
            }

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): HistoryTransactionViewHolder {
                return HistoryTransactionViewHolder(parent)
            }
        }

        override fun bind(model: HistoryItem) {
            historyList.adapter = historyListAdapter
            historyList.isNestedScrollingEnabled = false

            model.creditCardLiveData.observe(
                itemView.context.toActivity() as LifecycleOwner
            ) {
                historyListAdapter.transactions = it.history
                if (it.currency != null) {
                    historyListAdapter.currency = it.currency
                }
                historyListAdapter.notifyDataSetChanged()
            }
        }
    }

    class HistoryItem(
        val creditCardLiveData: LiveData<CreditCard>
    ): ViewTypeItem
}