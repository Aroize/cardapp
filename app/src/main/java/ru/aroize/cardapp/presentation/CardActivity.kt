package ru.aroize.cardapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ru.aroize.cardapp.R
import ru.aroize.cardapp.delegate.ViewTypeAdapterDelegate
import ru.aroize.cardapp.domain.dto.CreditCard
import ru.aroize.cardapp.presentation.ui.card.CardViewTypeAdapter
import ru.aroize.cardapp.presentation.ui.CurrencyPickerAdapter
import ru.aroize.cardapp.presentation.ui.HeaderViewTypeAdapter
import ru.aroize.cardapp.presentation.ui.card.CardClickCallback
import ru.aroize.cardapp.presentation.ui.history.HistoryViewTypeAdapter
import ru.aroize.cardapp.vm.CardViewModel

@Suppress("UNCHECKED_CAST")
class CardActivity : AppCompatActivity(), CardClickCallback {

    private lateinit var blocksList: RecyclerView
    private lateinit var launcher: ActivityResultLauncher<Pair<List<CreditCard>, CreditCard>>

    private val model: CardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launcher = registerForActivityResult(object : ActivityResultContract<Pair<List<CreditCard>, CreditCard>, CreditCard>() {
            override fun createIntent(context: Context, input: Pair<List<CreditCard>, CreditCard>): Intent {
                val intent = Intent(this@CardActivity, CardsListActivity::class.java)
                val (cards, chosen) = input
                val cardNumbers: Array<String> = cards.map { it.cardNumber }.toTypedArray()
                val cardTypes: IntArray = cards.map { it.type.ordinal }.toTypedArray().toIntArray()
                intent.putExtra(CardsListActivity.CARD_NUMBERS, cardNumbers)
                intent.putExtra(CardsListActivity.CARD_TYPES, cardTypes)
                intent.putExtra(CardsListActivity.CHOSEN_CARD_NUMBER, chosen.cardNumber)
                return intent
            }

            override fun parseResult(resultCode: Int, intent: Intent?): CreditCard? {
                if (resultCode == RESULT_OK && intent != null) {
                    val number = intent.getStringExtra(CARD_NUMBER)
                    val cards = model.cards.value ?: return null
                    return cards.find { it.cardNumber == number }
                }
                return null
            }
        }) {
            it ?: return@registerForActivityResult
            model.chosenCard.postValue(it)
        }
        supportActionBar?.hide()
        setContentView(R.layout.card_activity)
        blocksList = findViewById(R.id.card_list)
        blocksList.isNestedScrollingEnabled = false
        blocksList.adapter = createAdapter()
    }

    override fun cardClicked() {
        launcher.launch((model.cards.value as List<CreditCard>) to (model.chosenCard.value as CreditCard))
    }

    private fun createAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        val delegate = ViewTypeAdapterDelegate()

        delegate.addAdapter(HeaderViewTypeAdapter())
        delegate.addAdapter(CardViewTypeAdapter())
        delegate.addAdapter(CurrencyPickerAdapter())
        delegate.addAdapter(HistoryViewTypeAdapter())

        delegate.addItem(HeaderViewTypeAdapter.HeaderItem())
        delegate.addItem(CardViewTypeAdapter.CardItem(model.chosenCard, this))
        delegate.addItem(CurrencyPickerAdapter.CurrencyPickerViewType(model.currencyList, model.chosenCurrency))
        delegate.addItem(HistoryViewTypeAdapter.HistoryItem(model.chosenCard))

        return delegate as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    companion object {
        const val CARD_NUMBER = "extra.card.number"
    }
}