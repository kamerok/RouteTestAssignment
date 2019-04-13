package com.kamer.aviasalestest.dependency

import com.kamer.aviasalestest.data.SelectedCityStorage
import com.kamer.aviasalestest.features.main.MainRouter
import com.kamer.aviasalestest.features.main.MainViewModel
import com.kamer.aviasalestest.features.select.SelectCityViewModel


object ServiceLocator {

    fun buildMainViewModel(router: MainRouter) = MainViewModel(selectedCityStorage, router)

    fun buildSelectCityViewModel(isOrigin: Boolean) = SelectCityViewModel(isOrigin)

    private val selectedCityStorage by lazy { SelectedCityStorage() }

}