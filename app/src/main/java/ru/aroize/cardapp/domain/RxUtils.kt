package ru.aroize.cardapp.domain

import hu.akarnokd.rxjava3.interop.RxJavaInterop
import rx.Single

fun<T> Single<T>.toRx3() = RxJavaInterop.toV3Single(this)