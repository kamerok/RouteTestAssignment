package com.kamer.aviasalestest.features.select

import com.kamer.aviasalestest.data.SearchCityRepository
import com.kamer.aviasalestest.data.SelectedCityStorage
import com.kamer.aviasalestest.model.City
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SelectCityInteractor(
    private val storage: SelectedCityStorage,
    private val searchCityRepository: SearchCityRepository
) {

    fun findCities(query: String): Single<List<City>> =
        searchCityRepository.searchCities(query)
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