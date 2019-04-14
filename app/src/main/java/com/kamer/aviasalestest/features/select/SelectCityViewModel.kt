package com.kamer.aviasalestest.features.select

import com.kamer.aviasalestest.R
import com.kamer.aviasalestest.model.City
import com.kamer.aviasalestest.utils.StringProvider
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject


class SelectCityViewModel(
    private val isOrigin: Boolean,
    interactor: SelectCityInteractor,
    router: SelectCityRouter,
    private val stringProvider: StringProvider
) {

    private val inputStream: Subject<SelectCityEvent> = PublishSubject.create()

    private val inputTransformer: ObservableTransformer<SelectCityEvent, Action> = ObservableTransformer { upstream ->
        Observable.merge(
            upstream.ofType(QueryChanged::class.java)
                .switchMap { event ->
                    if (event.query.isEmpty()) {
                        Observable.just(QueryCleared)
                    } else {
                        interactor.findCities(event.query)
                            .map<Action> { LoadingSuccess(it) }
                            .onErrorReturn { LoadingError }
                            .toObservable()
                            .startWith(LoadingStarted)
                    }
                },
            upstream.ofType(CitySelected::class.java)
                .flatMap<Action> { event ->
                    interactor.selectCity(event.city, isOrigin)
                        .onErrorComplete()
                        .doOnComplete { router.closeScreen() }
                        .toObservable()
                }
        )
    }

    val state: Observable<SelectCityUiModel> by lazy {
        inputStream.compose(inputTransformer)
            .scan(initialState()) { previous, action -> reduce(previous, action) }
            .distinctUntilChanged()
            .replay(1)
            .autoConnect()
    }

    fun postEvent(event: SelectCityEvent) = inputStream.onNext(event)

    private fun reduce(previous: SelectCityUiModel, action: Action): SelectCityUiModel =
        when (action) {
            LoadingStarted -> previous.copy(items = listOf(LoadingItem))
            LoadingError -> previous.copy(items = listOf(MessageItem(stringProvider.provide(R.string.select_error))))
            is LoadingSuccess -> previous.copy(
                items = if (action.data.isEmpty()) {
                    listOf(MessageItem(stringProvider.provide(R.string.select_nothing_found)))
                } else {
                    action.data.map { CityItem(it) }
                }
            )
            QueryCleared -> previous.copy(items = listOf(MessageItem(stringProvider.provide(R.string.select_prompt))))
        }

    private fun initialState() =
        SelectCityUiModel(
            hint = stringProvider.provide(if (isOrigin) R.string.select_title_origin else R.string.select_title_destination),
//            query = "",
            items = listOf(MessageItem(stringProvider.provide(R.string.select_prompt)))
        )

}

private sealed class Action

private object QueryCleared : Action()

private object LoadingStarted : Action()

private data class LoadingSuccess(val data: List<City>) : Action()

private object LoadingError : Action()