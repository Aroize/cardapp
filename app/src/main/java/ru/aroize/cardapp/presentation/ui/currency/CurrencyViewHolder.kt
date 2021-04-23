package ru.aroize.cardapp.presentation.ui.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.aroize.cardapp.R
import ru.aroize.cardapp.domain.dto.Currency
import ru.aroize.cardapp.presentation.hide
import ru.aroize.cardapp.presentation.show

class CurrencyViewHolder(
    parent: ViewGroup,
    callback: CurrencyClickedCallback
): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.currency_item, parent, false)
) {

    private lateinit var currency: Currency
    private val charCode = itemView.findViewById<TextView>(R.id.char_code_with_symbol)
    private val currencySymbols = itemView.findViewById<TextView>(R.id.currency_symbols)

    init {
        itemView.setOnClickListener {
            callback.clicked(currency)
        }
    }

    fun bind(
        currency: Currency,
        isClicked: Boolean,
        isFirst: Boolean,
        isLast: Boolean
    ) {
        this.currency = currency
        val resources = itemView.context.resources
        val defaultMargin = resources.getDimension(R.dimen.default_currency_margin).toInt()
        val leftMargin = if (isFirst) {
            resources.getDimension(R.dimen.recycler_first_item_padding).toInt() + defaultMargin
        } else {
            defaultMargin
        }
        val rightMargin = if (isLast) {
            resources.getDimension(R.dimen.recycler_first_item_padding).toInt() + defaultMargin
        } else {
            defaultMargin
        }
        itemView.layoutParams = ViewGroup.MarginLayoutParams(itemView.layoutParams).apply {
            setMargins(leftMargin, defaultMargin, rightMargin, defaultMargin)
        }

        val cardBgId = if (isClicked) {
            R.drawable.rounded_rect_blue_16
        } else {
            R.drawable.rounded_rect_16
        }
        itemView.background = ContextCompat.getDrawable(itemView.context, cardBgId)
        currencySymbols.text = currency.currencyName
        if (currency.charCode != null) {
            charCode.show()
            charCode.text = currency.charCode
        } else {
            charCode.hide()
        }
        val textColorId = if (isClicked) {
            R.color.white
        } else {
            R.color.gray_text
        }
        val textColor = ContextCompat.getColor(itemView.context, textColorId)
        currencySymbols.setTextColor(textColor)
        charCode.setTextColor(textColor)
    }
}