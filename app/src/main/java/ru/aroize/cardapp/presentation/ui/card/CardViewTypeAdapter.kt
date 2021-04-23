package ru.aroize.cardapp.presentation.ui.card

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import ru.aroize.cardapp.R
import ru.aroize.cardapp.MoneyFromatter
import ru.aroize.cardapp.delegate.ViewTypeAdapterDelegate
import ru.aroize.cardapp.delegate.ViewTypeHolder
import ru.aroize.cardapp.delegate.ViewTypeItem
import ru.aroize.cardapp.domain.dto.CreditCard
import ru.aroize.cardapp.presentation.toActivity

@Suppress("UNCHECKED_CAST")
class CardViewTypeAdapter : ViewTypeAdapterDelegate.ViewTypeAdapter {
    override fun createViewHolder(parent: ViewGroup): ViewTypeHolder<ViewTypeItem> {
        return CardViewHolder(parent) as ViewTypeHolder<ViewTypeItem>
    }

    override fun isForViewType(item: ViewTypeItem): Boolean {
        return item is CardItem
    }

    class CardViewHolder(parent: ViewGroup) : ViewTypeHolder<CardItem>(
        FrameLayout(parent.context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            clipToPadding = false
            val view = CardView(context)
            view.tag = cardTag
            addView(view)
        }
    ) {

        private val cardView: CardView = itemView.findViewWithTag(cardTag)

        override fun bind(model: CardItem) {

            itemView.setOnClickListener { model.cardClickCallback.cardClicked() }

            itemView.updatePadding(top = itemView.context.resources.getDimension(R.dimen.card_padding).toInt())

            model.cardLiveData.observe(itemView.context.toActivity() as LifecycleOwner) {
                val cardIcon = when(it.type) {
                    CreditCard.CardType.MASTERCARD -> R.drawable.ic_mastercard
                    CreditCard.CardType.VISA -> R.drawable.ic_visa
                    CreditCard.CardType.UNIONPAY -> R.drawable.ic_union_pay
                }
                cardView.loadCardCompany(ContextCompat.getDrawable(cardView.context, cardIcon))
                cardView.setCardNumber(it.cardNumber)
                cardView.setCardHolder(it.cardHolder)
                val dollarBalance = MoneyFromatter.format(it.balance)
                cardView.setDollarBalance("\$$dollarBalance")
                cardView.setCardExpirationTime(it.valid)
                if (it.currencyBalance != null && it.currency != null) {
                    val currencyBalance = MoneyFromatter.format(it.currencyBalance, it.currency)
                    cardView.setCurrencyBalance(currencyBalance)
                } else {
                    cardView.setCurrencyBalance("\$$dollarBalance")
                }
            }
        }

        companion object {
            private const val cardTag = "card.view"
        }
    }

    class CardItem(
        val cardLiveData: LiveData<CreditCard>,
        val cardClickCallback: CardClickCallback
    ): ViewTypeItem
}