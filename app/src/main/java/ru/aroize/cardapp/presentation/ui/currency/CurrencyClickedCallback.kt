package ru.aroize.cardapp.presentation.ui.currency

import ru.aroize.cardapp.domain.dto.Currency

interface CurrencyClickedCallback {
    fun clicked(currency: Currency)
}