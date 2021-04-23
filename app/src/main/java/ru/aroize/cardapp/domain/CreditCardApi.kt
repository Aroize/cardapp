package ru.aroize.cardapp.domain

import org.json.JSONObject
import retrofit2.http.GET
import rx.Single

interface CreditCardApi {
    @GET("/test/android/v1/users.json")
    fun creditCards(): Single<JSONObject>
}