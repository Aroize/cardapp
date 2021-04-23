package ru.aroize.cardapp.domain.dto

data class Currency(
    val charCode: String?,
    val currencyName: String,
    var value: Float
)
