package com.kamer.aviasalestest.features.select

import com.kamer.aviasalestest.model.City


sealed class SelectCityItem

data class CityItem(val city: City) : SelectCityItem()

data class MessageItem(val message: String) : SelectCityItem()

object LoadingItem : SelectCityItem()