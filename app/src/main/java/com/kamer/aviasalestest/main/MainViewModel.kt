package com.kamer.aviasalestest.main

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
                    fromName = origin.name,
                    fromIata = origin.iata,
                    toName = destination.name,
                    toIata = destination.iata
                )
            }
        )

    fun postEvent(event: MainEvent) {
        when(event) {
            Search -> router.showProgress(storage.getOrigin().blockingFirst(), storage.getDestination().blockingFirst())
            SelectOrigin -> router.showSelectCity(true)
            SelectDestination -> router.showSelectCity(false)
        }
    }
}