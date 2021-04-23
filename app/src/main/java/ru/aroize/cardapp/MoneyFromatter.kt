package ru.aroize.cardapp

import ru.aroize.cardapp.domain.dto.Currency

object MoneyFromatter {
    fun format(amount: Float): String {
        val parts = amount.toString().split('.')
        val even = parts[0]
        val formatted = StringBuilder()
        even.reversed().forEachIndexed { index, c ->
            formatted.append(c)
            if ((index + 1) % 3 == 0)
                formatted.append(' ')
        }
        formatted.reverse()
        if (parts.size == 2) {
            var mantissa = parts[1]
            if (mantissa.length > 2)
                mantissa = mantissa.substring(0..1)
            formatted.append('.').append(mantissa)
        }
        return formatted.toString()
    }

    fun format(amount: Float, currency: Currency): String {
        val formatted = format(amount).replace('.', ',')
        return if (currency.charCode == null) {
            StringBuilder(formatted)
                .append(' ')
                .append(currency.currencyName)
                .toString()
        } else {
            StringBuilder(currency.charCode)
                .append(' ')
                .append(formatted)
                .toString()
        }
    }
}