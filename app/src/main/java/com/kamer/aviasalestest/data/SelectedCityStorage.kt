package com.kamer.aviasalestest.data

import com.kamer.aviasalestest.AirportPoint
import com.kamer.aviasalestest.model.City
import io.reactivex.Observable


class SelectedCityStorage {

    private val sydney = AirportPoint("SYD", -34.0, 151.0)
    private val moscow = AirportPoint("MOW", 55.752041, 37.617508)
    private val newYork = AirportPoint("NYC", 40.75603, -73.986956)

    fun getOrigin(): Observable<City> = Observable.just(City(0, "Москва", "MOW", 55.752041, 37.617508))

    fun getDestination(): Observable<City> = Observable.just(City(0, "Нью-Йорк", "NYC", 40.75603, -73.986956))

}