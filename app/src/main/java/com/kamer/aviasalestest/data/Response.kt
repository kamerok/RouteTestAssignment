package com.kamer.aviasalestest.data


data class Response(
    val cities: List<ResponseCity>
)

data class ResponseCity(
    val id: Long,
    val city: String,
    val location: Location,
    val iata: Array<String>
)

data class Location(
    val lat: Double,
    val lon: Double
)