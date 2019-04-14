package com.kamer.aviasalestest.data

import com.kamer.aviasalestest.model.City
import io.reactivex.Single


class SearchCityRepository(private val searchCityService: SearchCityService) {

    fun searchCities(query: String): Single<List<City>> =
        searchCityService.findCities(query)
            .map { response ->
                response.cities.map { responseCity ->
                    City(
                        id = responseCity.id,
                        name = responseCity.city,
                        iata = responseCity.iata.firstOrNull() ?: "",
                        latitude = responseCity.location.lat,
                        longitude = responseCity.location.lon
                    )
                }
            }

}