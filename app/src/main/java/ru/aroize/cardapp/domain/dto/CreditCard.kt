package ru.aroize.cardapp.domain.dto

data class CreditCard(
    val cardNumber: String = "",
    val cardHolder: String = "",
    val type: CardType = CardType.MASTERCARD,
    val valid: String = "",
    val balance: Float = 0f,
    val currencyBalance: Float? = null,
    val currency: Currency? = null,
    val history: List<Transaction> = emptyList()
) {
    enum class CardType {
        VISA,
        MASTERCARD,
        UNIONPAY
    }
}