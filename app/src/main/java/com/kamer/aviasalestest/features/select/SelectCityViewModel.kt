package com.kamer.aviasalestest.features.select

import com.kamer.aviasalestest.R
import com.kamer.aviasalestest.model.City
import com.kamer.aviasalestest.utils.StringProvider
import io.reactivex.Observable


class SelectCityViewModel(
    isOrigin: Boolean,
    interactor: SelectCityInteractor,
    router: SelectCityRouter,
    stringProvider: StringProvider
) {

    val state: Observable<SelectCityUiModel> = Observable.just(
        SelectCityUiModel(
            hint = stringProvider.provide(if (isOrigin) R.string.select_title_origin else R.string.select_title_destination),
            query = "",
            items = listOf(MessageItem(stringProvider.provide(R.string.select_empty)))
        )
    )

    fun postEvent(event: SelectCityEvent) {

    }

}