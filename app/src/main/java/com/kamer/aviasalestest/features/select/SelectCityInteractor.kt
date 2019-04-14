package com.kamer.aviasalestest.features.select

import com.kamer.aviasalestest.data.SelectedCityStorage
import com.kamer.aviasalestest.model.City
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SelectCityInteractor(private val storage: SelectedCityStorage) {

    fun findCities(query: String): Single<List<City>> =
        Single
            .just(
                listOf(
                    City(0, "Sydney", "SYD", -34.0, 151.0),
                    City(12153, "Москва", "MOW", 55.752041, 37.617508),
                    City(20857, "Нью-Йорк", "NYC", 40.75603, -73.986956)
                )
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun selectCity(city: City, isOrigin: Boolean): Completable =
        if (isOrigin) {
            storage.setOrigin(city)
        } else {
            storage.setDestination(city)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}