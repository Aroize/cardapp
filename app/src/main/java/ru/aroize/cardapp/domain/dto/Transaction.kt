package ru.aroize.cardapp.domain.dto

import org.json.JSONObject

data class Transaction(
    val title: String,
    val iconUrl: String,
    val date: String,
    val amount: Float
) {
    companion object {
        fun parse(json: JSONObject): Transaction {
            return Transaction(
                json.getString("title"),
                json.getString("icon_url"),
                json.getString("date"),
                json.getString("amount").toFloat()
            )
        }
    }
}
