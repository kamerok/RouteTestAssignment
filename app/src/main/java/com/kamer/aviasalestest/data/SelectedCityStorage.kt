package com.kamer.aviasalestest.data

import android.content.Context
import com.kamer.aviasalestest.model.City
import io.reactivex.Completable
import io.reactivex.Observable


class SelectedCityStorage(context: Context) {

    private val sydney = City(0, "Sydney", "SYD", -34.0, 151.0)
    private val moscow = City(1, "Москва", "MOW", 55.752041, 37.617508)
    private val newYork = City(2, "Нью-Йорк", "NYC", 40.75603, -73.986956)

    fun getOrigin(): Observable<City> = Observable.just(moscow)

    fun getDestination(): Observable<City> = Observable.just(newYork)

    fun setOrigin(city: City): Completable = Completable.complete()

    fun setDestination(city: City): Completable = Completable.complete()

}