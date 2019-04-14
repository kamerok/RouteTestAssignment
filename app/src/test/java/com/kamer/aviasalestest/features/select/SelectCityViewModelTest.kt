package com.kamer.aviasalestest.features.select

import com.kamer.aviasalestest.R
import com.kamer.aviasalestest.utils.StringProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.verify

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

    @Test
    fun `Empty query on start`() {
        buildViewModel()

        assertThat(getState().query).isEmpty()
    }

    @Test
    fun `Show search prompt on start`() {
        buildViewModel()

        assertThat(getState().items).containsOnly(MessageItem(stringProvider.provide(R.string.select_empty)))
    }

    private fun buildViewModel(isOrigin: Boolean = true) {
        viewModel = SelectCityViewModel(isOrigin, interactor, router, stringProvider)
    }

    private fun getState(): SelectCityUiModel = viewModel.state.test().values().first()

}

fun StringProvider.mock(resId: Int) {
    whenever(provide(resId)).thenReturn(resId.toString())
}

fun StringProvider.assert(resId: Int): String {
    verify(this).provide(resId)
    return resId.toString()
}