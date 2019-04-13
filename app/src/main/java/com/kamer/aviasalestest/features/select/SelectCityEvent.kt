package com.kamer.aviasalestest.features.select

import com.kamer.aviasalestest.model.City


sealed class SelectCityEvent

data class QueryChanged(val query: String) : SelectCityEvent()

data class CitySelected(val city: City) : SelectCityEvent()