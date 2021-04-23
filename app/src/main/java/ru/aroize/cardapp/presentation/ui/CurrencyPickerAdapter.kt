package ru.aroize.cardapp.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import ru.aroize.cardapp.R
import ru.aroize.cardapp.delegate.ViewTypeAdapterDelegate
import ru.aroize.cardapp.delegate.ViewTypeHolder
import ru.aroize.cardapp.delegate.ViewTypeItem
import ru.aroize.cardapp.domain.dto.Currency
import ru.aroize.cardapp.presentation.toActivity
import ru.aroize.cardapp.presentation.ui.currency.CurrencyAdapter

@Suppress("UNCHECKED_CAST")
class CurrencyPickerAdapter : ViewTypeAdapterDelegate.ViewTypeAdapter {

    override fun isForViewType(item: ViewTypeItem): Boolean {
        return item is CurrencyPickerViewType
    }

    override fun createViewHolder(parent: ViewGroup): ViewTypeHolder<ViewTypeItem> {
        return CurrencyPickerViewHolder(parent) as ViewTypeHolder<ViewTypeItem>
    }

    class CurrencyPickerViewHolder(parent: ViewGroup): ViewTypeHolder<CurrencyPickerViewType>(
        LayoutInflater.from(parent.context).inflate(R.layout.currency_picker_item, parent, false)
    ) {

        private lateinit var currencyAdapter: CurrencyAdapter

        private val currencyRecycler = itemView.findViewById<RecyclerView>(R.id.currency_list)

        override fun bind(model: CurrencyPickerViewType) {
            val top = itemView.context.resources.getDimension(R.dimen.change_currency_padding).toInt()
            itemView.updatePadding(top = top)
            currencyAdapter = CurrencyAdapter(
                itemView.context.toActivity() as LifecycleOwner,
                model.currencyListLiveData,
                model.chosenCurrencyLiveData
            )
            currencyRecycler.adapter = currencyAdapter
        }
    }

    class CurrencyPickerViewType(
        val currencyListLiveData: LiveData<List<Currency>>,
        val chosenCurrencyLiveData: MutableLiveData<Currency>
    ): ViewTypeItem
}