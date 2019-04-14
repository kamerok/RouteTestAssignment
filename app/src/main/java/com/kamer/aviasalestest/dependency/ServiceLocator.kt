package com.kamer.aviasalestest.dependency

import android.content.Context
import com.kamer.aviasalestest.data.SelectedCityStorage
import com.kamer.aviasalestest.features.main.MainRouter
import com.kamer.aviasalestest.features.main.MainViewModel
import com.kamer.aviasalestest.features.select.SelectCityInteractor
import com.kamer.aviasalestest.features.select.SelectCityRouter
import com.kamer.aviasalestest.features.select.SelectCityViewModel
import com.kamer.aviasalestest.utils.StringProvider


object ServiceLocator {

    private val selectedCityStorage by lazy { SelectedCityStorage() }

    fun buildMainViewModel(router: MainRouter) = MainViewModel(selectedCityStorage, router)

    fun buildSelectCityViewModel(context: Context, isOrigin: Boolean) = SelectCityViewModel(isOrigin, SelectCityInteractor(), SelectCityRouter(), buildStringProvider(context))

    private fun buildStringProvider(context: Context): StringProvider = StringProvider(context)

}