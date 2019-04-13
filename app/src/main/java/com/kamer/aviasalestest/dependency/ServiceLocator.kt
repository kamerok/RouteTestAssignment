package com.kamer.aviasalestest.dependency

import com.kamer.aviasalestest.data.SelectedCityStorage
import com.kamer.aviasalestest.features.main.MainRouter
import com.kamer.aviasalestest.features.main.MainViewModel


object ServiceLocator {

    fun buildMainViewModel(router: MainRouter) = MainViewModel(selectedCityStorage, router)

    private val selectedCityStorage by lazy { SelectedCityStorage() }

}