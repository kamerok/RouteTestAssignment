package com.kamer.aviasalestest.model


data class City(
    val id: Long,
    val name: String,
    val iata: String,
    val latitude: Double,
    val longitude: Double
)