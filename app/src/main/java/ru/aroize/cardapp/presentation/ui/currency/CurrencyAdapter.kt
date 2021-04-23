package ru.aroize.cardapp.presentation.ui.currency

import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import ru.aroize.cardapp.domain.dto.Currency

class CurrencyAdapter(
    owner: LifecycleOwner,
    currencyListLiveData: LiveData<List<Currency>>,
    private val currencyLiveData: MutableLiveData<Currency>
): RecyclerView.Adapter<CurrencyViewHolder>(), CurrencyClickedCallback {

    private val currencyList = arrayListOf<Currency>()
    private var chosenCurrencyIndex = 0

    init {
        currencyListLiveData.observe(owner) {
            currencyList.clear()
            currencyList.addAll(it)
            notifyDataSetChanged()
            currencyLiveData.value = it.first()
        }
    }


    override fun clicked(currency: Currency) {
        val newChosenIndex = currencyList.indexOfFirst { it === currency }
        if (chosenCurrencyIndex == newChosenIndex)
            return
        val unclicked = chosenCurrencyIndex
        chosenCurrencyIndex = newChosenIndex
        notifyItemChanged(unclicked)
        notifyItemChanged(chosenCurrencyIndex)

        currencyLiveData.value = currency
    }

    override fun getItemCount() = currencyList.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(
            currencyList[position],
            position == chosenCurrencyIndex,
            position == 0,
            position == itemCount - 1
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(parent, this)
    }
}