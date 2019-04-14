package com.kamer.aviasalestest.dependency

import android.content.Context
import com.google.gson.Gson
import com.kamer.aviasalestest.data.SearchCityRepository
import com.kamer.aviasalestest.data.SearchCityService
import com.kamer.aviasalestest.data.SelectedCityStorage
import com.kamer.aviasalestest.features.main.MainRouter
import com.kamer.aviasalestest.features.main.MainViewModel
import com.kamer.aviasalestest.features.select.SelectCityInteractor
import com.kamer.aviasalestest.features.select.SelectCityRouter
import com.kamer.aviasalestest.features.select.SelectCityViewModel
import com.kamer.aviasalestest.utils.StringProvider
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ServiceLocator(
    private val appContext: Context
) {

    private val selectedCityStorage: SelectedCityStorage by lazy { SelectedCityStorage(appContext, gson) }

    private val gson: Gson by lazy { Gson() }

    private val searchService: SearchCityService by lazy {
        Retrofit.Builder()
            .baseUrl("https://yasen.hotellook.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(SearchCityService::class.java)
    }

    private val searchCityRepository: SearchCityRepository by lazy {
        SearchCityRepository(searchService)
    }

    fun buildMainViewModel(router: MainRouter) = MainViewModel(selectedCityStorage, router)

    fun buildSelectCityViewModel(isOrigin: Boolean, router: SelectCityRouter) =
        SelectCityViewModel(
            isOrigin,
            buildSelectCityInteractor(),
            router,
            buildStringProvider()
        )

    private fun buildSelectCityInteractor() = SelectCityInteractor(selectedCityStorage, searchCityRepository)


    private fun buildStringProvider(): StringProvider = StringProvider(appContext)

}