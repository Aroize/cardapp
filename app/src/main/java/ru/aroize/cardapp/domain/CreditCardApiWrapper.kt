package ru.aroize.cardapp.domain

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import ru.aroize.cardapp.domain.dto.CreditCard
import ru.aroize.cardapp.domain.dto.Transaction

class CreditCardApiWrapper(
    private val creditCardApi: CreditCardApi
) {

    fun creditCards(): Single<List<CreditCard>> = creditCardApi.creditCards()
        .toRx3()
        .map { parse(it) }
        .subscribeOn(Schedulers.io())

    private fun parse(json: JSONObject): List<CreditCard> {
        val users = json.getJSONArray("users")
        val result = arrayListOf<CreditCard>()
        for (i in 0 until users.length()) {
            val obj = users.getJSONObject(i)
            val type = when (obj.getString("type")) {
                "mastercard" -> CreditCard.CardType.MASTERCARD
                "visa" -> CreditCard.CardType.VISA
                "unionpay" -> CreditCard.CardType.UNIONPAY
                else -> throw IllegalArgumentException("Unsupported card type")
            }
            val list = obj.getJSONArray("transaction_history").map { Transaction.parse(it) }.toMutableList()
            list += list
            list += list
            result.add(
                CreditCard(
                    obj.getString("card_number"),
                    obj.getString("cardholder_name"),
                    type,
                    obj.getString("valid"),
                    obj.getDouble("balance").toFloat(),
                    history = list
                )
            )
        }
        return result
    }

    private inline fun<T> JSONArray.map(parser: (JSONObject) -> T): List<T> {
        val result = ArrayList<T>(length())
        for (i in 0 until length()) {
            result.add(parser.invoke(getJSONObject(i)))
        }
        return result
    }
}