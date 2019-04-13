package com.kamer.aviasalestest.features.main

import com.kamer.aviasalestest.data.SelectedCityStorage
import io.reactivex.Observable
import io.reactivex.functions.BiFunction


class MainViewModel(
    private val storage: SelectedCityStorage,
    private val router: MainRouter
) {

    val state: Observable<MainUiModel> =
        Observable.zip(
            storage.getOrigin(),
            storage.getDestination(),
            BiFunction { origin, destination ->
                MainUiModel(
                    originName = origin.name,
                    originIata = origin.iata,
                    destinationName = destination.name,
                    destinationIata = destination.iata
                )
            }
        )

    fun postEvent(event: MainEvent) {
        when(event) {
            Search -> router.showProgress(storage.getOrigin().blockingFirst(), storage.getDestination().blockingFirst())
            SelectOrigin -> router.showSelectCity(isOrigin = true)
            SelectDestination -> router.showSelectCity(isOrigin = false)
        }
    }
}