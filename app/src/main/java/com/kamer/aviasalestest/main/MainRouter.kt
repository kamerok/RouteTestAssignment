package com.kamer.aviasalestest.main

import com.kamer.aviasalestest.model.City


interface MainRouter {

    fun showProgress(origin: City, destination: City)

    fun showSelectCity(isSource: Boolean)

}