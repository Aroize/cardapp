package ru.aroize.cardapp.presentation.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.aroize.cardapp.MoneyFromatter
import ru.aroize.cardapp.R
import ru.aroize.cardapp.domain.dto.Currency
import ru.aroize.cardapp.domain.dto.Transaction
import kotlin.math.absoluteValue

class HistoryTransactionViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.history_transaction_item, parent, false)
) {
    private val transactionIcon = itemView.findViewById<ImageView>(R.id.transaction_icon)
    private val title = itemView.findViewById<TextView>(R.id.title)
    private val date = itemView.findViewById<TextView>(R.id.date)
    private val paid = itemView.findViewById<TextView>(R.id.dollar_paid)
    private val currencyPaid = itemView.findViewById<TextView>(R.id.currency_paid)
    private val currencySymbol = itemView.findViewById<TextView>(R.id.sign_with_symbol)

    @SuppressLint("SetTextI18n")
    fun bind(transaction: Transaction, currency: Currency) {
        Glide.with(transactionIcon)
            .load(transaction.iconUrl)
            .into(transactionIcon)
        title.text = transaction.title
        date.text = transaction.date
        paid.text = "\$${transaction.amount.absoluteValue}"
        val symbols = StringBuilder()
        if (transaction.amount < 0) {
            symbols.append('-')
        } else {
            symbols.append('+')
        }
        symbols.append(currency.charCode ?: currency.currencyName)
        currencySymbol.text = symbols.toString()
        currencyPaid.text = MoneyFromatter.format(transaction.amount.absoluteValue / currency.value)
    }
}