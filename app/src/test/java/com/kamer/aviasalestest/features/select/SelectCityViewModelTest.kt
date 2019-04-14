package com.kamer.aviasalestest.features.select

import com.kamer.aviasalestest.R
import com.kamer.aviasalestest.model.City
import com.kamer.aviasalestest.utils.StringProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.verify
import java.lang.Exception

class SelectCityViewModelTest {

    private val interactor: SelectCityInteractor = mock()
    private val router: SelectCityRouter = mock()
    private val stringProvider: StringProvider = mock {
        on { provide(any()) }.then { "mock" }
    }

    private lateinit var viewModel: SelectCityViewModel

    @Test
    fun `Show origin hint on start`() {
        stringProvider.mock(R.string.select_title_origin)
        buildViewModel(isOrigin = true)

        assertThat(getState().hint).isEqualTo(stringProvider.assert(R.string.select_title_origin))
    }

    @Test
    fun `Show destination hint on start`() {
        stringProvider.mock(R.string.select_title_destination)
        buildViewModel(isOrigin = false)

        assertThat(getState().hint).isEqualTo(stringProvider.assert(R.string.select_title_destination))
    }

    /*@Test
    fun `Empty query on start`() {
        buildViewModel()

        assertThat(getState().query).isEmpty()
    }*/

    @Test
    fun `Show search prompt on start`() {
        buildViewModel()

        assertThat(getState().items).containsOnly(MessageItem(stringProvider.provide(R.string.select_prompt)))
    }

    @Test
    fun `Start city search on input`() {
        whenever(interactor.findCities(any())).then { Single.never<List<City>>() }

        buildViewModel()
        val query = "query"
        viewModel.postEvent(QueryChanged(query))

        verify(interactor).findCities(query)
    }

    @Test
    fun `Show loading on start search`() {
        whenever(interactor.findCities(any())).then { Single.never<List<City>>() }

        buildViewModel()
        viewModel.postEvent(QueryChanged("query"))

        assertThat(getState().items).containsOnly(LoadingItem)
    }

    @Test
    fun `Show error message on search error`() {
        stringProvider.mock(R.string.select_error)
        whenever(interactor.findCities(any())).then { Single.error<List<City>>(Exception()) }

        buildViewModel()
        viewModel.postEvent(QueryChanged("query"))

        assertThat(getState().items).containsOnly(MessageItem(stringProvider.provide(R.string.select_error)))
    }

    @Test
    fun `Show cities on search success`() {
        whenever(interactor.findCities(any())).then { Single.just(listOf(city(0), city(1))) }

        buildViewModel()
        viewModel.postEvent(QueryChanged("query"))

        assertThat(getState().items).containsOnly(
            CityItem(city(0)),
            CityItem(city(1))
        )
    }

    @Test
    fun `Show message on search empty result`() {
        stringProvider.mock(R.string.select_nothing_found)
        whenever(interactor.findCities(any())).then { Single.just(emptyList<City>()) }

        buildViewModel()
        viewModel.postEvent(QueryChanged("query"))

        assertThat(getState().items).containsOnly(MessageItem(stringProvider.assert(R.string.select_nothing_found)))
    }

    @Test
    fun `Drop previous search on new query`() {
        val delayedSearch = PublishSubject.create<List<City>>()
        val firstQuery = "q"
        val secondQuery = "w"
        whenever(interactor.findCities(firstQuery)).then { delayedSearch.firstOrError() }
        whenever(interactor.findCities(secondQuery)).then { Single.just(listOf(city(1))) }

        buildViewModel()
        viewModel.postEvent(QueryChanged(firstQuery))
        viewModel.postEvent(QueryChanged(secondQuery))
        delayedSearch.onNext(listOf(city(0)))

        assertThat(getState().items).containsOnly(CityItem(city(1)))
    }

    @Test
    fun `Show search prompt on empty query`() {
        stringProvider.mock(R.string.select_prompt)
        whenever(interactor.findCities(any())).then { Single.just(listOf(city(0))) }

        buildViewModel()
        viewModel.postEvent(QueryChanged("query"))
        viewModel.postEvent(QueryChanged(""))

        assertThat(getState().items).containsOnly(MessageItem(stringProvider.assert(R.string.select_prompt)))
    }

    private fun buildViewModel(isOrigin: Boolean = true) {
        viewModel = SelectCityViewModel(isOrigin, interactor, router, stringProvider)
        //view model respond on input events only when someone subscribed to the state
        viewModel.state.test()
    }

    private fun getState(): SelectCityUiModel = viewModel.state.test().values().first()

    private fun city(id: Long = 0) = City(id, "$id", "$id", 0.0, 0.0)
}

fun StringProvider.mock(resId: Int) {
    whenever(provide(resId)).thenReturn(resId.toString())
}

fun StringProvider.assert(resId: Int): String {
    verify(this, atLeastOnce()).provide(resId)
    return resId.toString()
}