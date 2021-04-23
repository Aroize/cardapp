package ru.aroize.cardapp.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.aroize.cardapp.domain.creditCardApiWrapper
import ru.aroize.cardapp.domain.currencyApiWrapper
import ru.aroize.cardapp.domain.dto.CreditCard
import ru.aroize.cardapp.domain.dto.Currency
import java.util.Currency as JCurrency

class CardViewModel : ViewModel() {

    val cards: MutableLiveData<List<CreditCard>> by lazy {
        MutableLiveData<List<CreditCard>>().apply {
            postValue(emptyList())
        }
    }
    val chosenCard: MutableLiveData<CreditCard> by lazy {
        loadCards()
        MutableLiveData()
    }
    val currencyList: MutableLiveData<List<Currency>> by lazy {
        loadCurrencies()
        MutableLiveData()
    }
    val chosenCurrency: MutableLiveData<Currency> by lazy {
        MutableLiveData<Currency>()
            .apply {
                observeForever {
                    cards.value = cards.value?.map { updateChosenCardCurrency(it)!! }
                    val card = updateChosenCardCurrency(chosenCard.value)
                    card ?: return@observeForever
                    chosenCard.value = card
                }
            }
    }

    private fun loadCards() {
        creditCardApiWrapper.creditCards()
            .subscribe({
                val updatedCards = it.map { card -> updateChosenCardCurrency(card) as CreditCard }
                cards.postValue(updatedCards)
                chosenCard.postValue(updatedCards.first())
            }, {})
    }
    private fun loadCurrencies() {
        currencyApiWrapper.currencies()
            .subscribe({ list ->
                val dollar = list.find { it.currencyName == "USD" }!!
                val dollarCurrencyList = list.map {
                    val currencySymbol = try {
                        JCurrency.getInstance(it.currencyName).symbol
                    } catch (e: IllegalArgumentException) {
                        null
                    }
                    it.copy(
                        charCode = currencySymbol,
                        value = it.value / dollar.value
                    )
                }
                currencyList.postValue(dollarCurrencyList)
            }, {
                Log.e("TAG", it.message, it)
            })
    }
    private fun updateChosenCardCurrency(card: CreditCard?): CreditCard? {
        card ?: return null
        val currency = chosenCurrency.value ?: return card
        return card.copy(
            currency = currency,
            currencyBalance = card.balance / currency.value
        )
    }
}