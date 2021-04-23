package ru.aroize.cardapp.presentation.ui.card

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.updatePadding
import ru.aroize.cardapp.R

class CardView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {

    init {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT,
            Gravity.CENTER_HORIZONTAL
        )
        val cardView = LayoutInflater.from(context).inflate(R.layout.card_view, this, false)
        addView(cardView)
    }

    private val cardCompany = findViewById<ImageView>(R.id.card_company)
    private val cardNumber = findViewById<TextView>(R.id.card_number)
    private val cardHolder = findViewById<TextView>(R.id.card_holder)
    private val dollarBalance = findViewById<TextView>(R.id.dollar_balance)
    private val currencyBalance = findViewById<TextView>(R.id.currency_balance)
    private val validDate = findViewById<TextView>(R.id.card_expiration_time)

    fun loadCardCompany(drawable: Drawable?) {
        cardCompany.setImageDrawable(drawable)
    }

    fun setCardNumber(number: String) {
        cardNumber.text = number
    }

    fun setCardHolder(name: String) {
        cardHolder.text = name
    }

    fun setDollarBalance(balance: String) {
        dollarBalance.text = balance
    }

    fun setCurrencyBalance(balance: String) {
        currencyBalance.text = balance
    }

    fun setCardExpirationTime(date: String) {
        validDate.text = date
    }

}