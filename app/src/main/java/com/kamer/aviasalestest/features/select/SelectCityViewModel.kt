package com.kamer.aviasalestest.features.select

import com.kamer.aviasalestest.model.City
import io.reactivex.Observable


class SelectCityViewModel(
    isOrigin: Boolean
) {

    val state: Observable<SelectCityUiModel> = Observable.just(
        SelectCityUiModel(
            hint = "isOrigin $isOrigin",
            query = "",
            items = listOf(
                CityItem(City(0, "Sydney", "SYD", -34.0, 151.0)),
                CityItem(City(1, "Москва", "MOW", 55.752041, 37.617508)),
                CityItem(City(2, "Нью-Йорк", "NYC", 40.75603, -73.986956)),
                MessageItem("Test message"),
                LoadingItem
            )
        )
    )

    fun postEvent(event: SelectCityEvent) {

    }

}