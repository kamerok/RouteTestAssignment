package com.kamer.aviasalestest.features.main

import com.kamer.aviasalestest.model.City


interface MainRouter {

    fun showProgress(origin: City, destination: City)

    fun showSelectCity(isOrigin: Boolean)

}