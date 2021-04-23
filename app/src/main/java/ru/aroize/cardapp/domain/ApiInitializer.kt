package ru.aroize.cardapp.domain

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory

lateinit var currencyApiWrapper: CurrencyApiWrapper
lateinit var creditCardApiWrapper: CreditCardApiWrapper

object ApiInitializer {
    fun init() {
        val currencyRetrofit = Retrofit.Builder()
            .baseUrl("https://www.cbr-xml-daily.ru/")
            .addConverterFactory(JSONConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
        val cardRetrofit = Retrofit.Builder()
            .baseUrl("https://hr.peterpartner.net/")
            .addConverterFactory(JSONConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
        val currencyApi = currencyRetrofit.create(CurrencyApi::class.java)
        currencyApiWrapper = CurrencyApiWrapper(currencyApi)
        val creditCardApi = cardRetrofit.create(CreditCardApi::class.java)
        creditCardApiWrapper = CreditCardApiWrapper(creditCardApi)
    }
}