package ru.aroize.cardapp.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.aroize.cardapp.R
import ru.aroize.cardapp.domain.dto.CreditCard

class CardsListActivity : AppCompatActivity() {

    private lateinit var cardsList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.cards_list_activity)
        findViewById<View>(R.id.back_btn).setOnClickListener {
            val result = Intent().also {
                val number = (cardsList.adapter as CardsListAdapter).chosenCard().cardNumber
                it.putExtra(CardActivity.CARD_NUMBER, number)
            }
            setResult(RESULT_OK, result)
            finish()
        }
        cardsList = findViewById(R.id.card_list)
        val cards = parseCards()
        val chosenCard = parseChosenCard()
        cardsList.adapter = CardsListAdapter(cards, chosenCard)
    }

    private fun parseCards(): List<CreditCard> {
        val numbers = intent.getStringArrayExtra(CARD_NUMBERS)!!
        val types = intent.getIntArrayExtra(CARD_TYPES)!!.map {
            when (it) {
                CreditCard.CardType.MASTERCARD.ordinal -> CreditCard.CardType.MASTERCARD
                CreditCard.CardType.VISA.ordinal -> CreditCard.CardType.VISA
                CreditCard.CardType.UNIONPAY.ordinal -> CreditCard.CardType.UNIONPAY
                else -> throw IllegalArgumentException("Unsupported card type")
            }
        }
        return numbers.zip(types).map { CreditCard(cardNumber = it.first, type = it.second) }
    }

    private fun parseChosenCard(): CreditCard {
        return CreditCard(
            cardNumber = intent.getStringExtra(CHOSEN_CARD_NUMBER)!!
        )
    }

    class CardsListItem(
        parent: ViewGroup,
        private val callback: CardChosenCallback
    ): RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.credit_card_item, parent, false)
    ) {

        private lateinit var card: CreditCard

        init {
            itemView.setOnClickListener { callback.chosen(card) }
        }

        interface CardChosenCallback {
            fun chosen(card: CreditCard)
        }

        private val cardCompany = itemView.findViewById<ImageView>(R.id.card_icon)
        private val cardNumber = itemView.findViewById<TextView>(R.id.card_number)
        private val marker = itemView.findViewById<ImageView>(R.id.mark)
        private val separator = itemView.findViewById<View>(R.id.separator)

        fun bind(creditCard: CreditCard, needSeparator: Boolean, isChosen: Boolean) {
            card = creditCard
            val cardIcon = when(creditCard.type) {
                CreditCard.CardType.MASTERCARD -> R.drawable.ic_mastercard
                CreditCard.CardType.VISA -> R.drawable.ic_visa
                CreditCard.CardType.UNIONPAY -> R.drawable.ic_union_pay
            }
            cardCompany.setImageDrawable(ContextCompat.getDrawable(itemView.context, cardIcon))
            cardNumber.text = creditCard.cardNumber
            if (isChosen) marker.show() else marker.hide()
            if (needSeparator) separator.show() else separator.hide()
        }
    }

    class CardsListAdapter(
        cards: List<CreditCard>,
        chosenCard: CreditCard
    ) : RecyclerView.Adapter<CardsListItem>(), CardsListItem.CardChosenCallback {

        private val cardsList = arrayListOf<CreditCard>()
        private var chosenCardIndex = 0

        init {
            cardsList.addAll(cards)
            chosenCardIndex = cardsList.indexOfFirst { it.cardNumber == chosenCard.cardNumber }
        }

        override fun chosen(card: CreditCard) {
            val chosenIndex = cardsList.indexOf(card)
            if (chosenIndex == chosenCardIndex)
                return
            val old = chosenCardIndex
            chosenCardIndex = chosenIndex
            notifyItemChanged(old)
            notifyItemChanged(chosenCardIndex)
        }

        fun chosenCard() = cardsList[chosenCardIndex]

        override fun getItemCount() = cardsList.size

        override fun onBindViewHolder(holder: CardsListItem, position: Int) {
            holder.bind(
                cardsList[position],
                position == itemCount - 1,
                position == chosenCardIndex
            )
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsListItem {
            return CardsListItem(parent, this)
        }
    }

    companion object {
        const val CARD_NUMBERS = "intent.card_numbers"
        const val CARD_TYPES = "intent.card.types"
        const val CHOSEN_CARD_NUMBER = "intent.chosen.card.number"
    }
}