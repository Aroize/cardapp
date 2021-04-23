package ru.aroize.cardapp.domain

import org.json.JSONObject
import retrofit2.http.GET
import rx.Single

interface CurrencyApi {
    @GET("/daily_json.js")
    fun currencies(): Single<JSONObject>
}