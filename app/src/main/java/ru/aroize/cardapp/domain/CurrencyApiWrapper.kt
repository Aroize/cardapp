package ru.aroize.cardapp.domain

import org.json.JSONObject
import ru.aroize.cardapp.domain.dto.Currency
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CurrencyApiWrapper(
    private val currencyApi: CurrencyApi
) {
    fun currencies(): Single<List<Currency>> {
        return currencyApi.currencies()
            .toRx3()
            .map { parseCurrencies(it) }
            .subscribeOn(Schedulers.io())
    }

    private fun parseCurrencies(json: JSONObject): List<Currency> {
        val valutes = json.getJSONObject("Valute")
        return valutes.keys().asSequence().map {
            val obj = valutes.getJSONObject(it)
            Currency(
                null,
                obj.getString("CharCode"),
                obj.getDouble("Value").toFloat()
            )
        }.toList()
    }
}