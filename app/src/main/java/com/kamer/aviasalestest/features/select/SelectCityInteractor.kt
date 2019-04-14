package com.kamer.aviasalestest.features.select

import com.kamer.aviasalestest.model.City
import io.reactivex.Completable
import io.reactivex.Single


class SelectCityInteractor {

    fun findCities(query: String): Single<List<City>> = Single.just(emptyList())

    fun selectCity(city: City, isOrigin: Boolean): Completable = Completable.complete()

}