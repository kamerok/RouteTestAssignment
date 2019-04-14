package com.kamer.aviasalestest.dependency

import android.content.Context
import com.kamer.aviasalestest.data.SelectedCityStorage
import com.kamer.aviasalestest.features.main.MainRouter
import com.kamer.aviasalestest.features.main.MainViewModel
import com.kamer.aviasalestest.features.select.SelectCityInteractor
import com.kamer.aviasalestest.features.select.SelectCityRouter
import com.kamer.aviasalestest.features.select.SelectCityViewModel
import com.kamer.aviasalestest.utils.StringProvider


class ServiceLocator(
    private val appContext: Context
) {

    private val selectedCityStorage by lazy { SelectedCityStorage(appContext) }

    fun buildMainViewModel(router: MainRouter) = MainViewModel(selectedCityStorage, router)

    fun buildSelectCityViewModel(isOrigin: Boolean) =
        SelectCityViewModel(
            isOrigin,
            buildSelectCityInteractor(),
            buildSelectCityRouter(),
            buildStringProvider()
        )

    private fun buildSelectCityInteractor() = SelectCityInteractor(selectedCityStorage)

    private fun buildSelectCityRouter() = SelectCityRouter()

    private fun buildStringProvider(): StringProvider = StringProvider(appContext)

}