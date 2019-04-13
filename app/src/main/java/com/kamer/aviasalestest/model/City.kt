package com.kamer.aviasalestest.model

import java.io.Serializable


data class City(
    val id: Long,
    val name: String,
    val iata: String,
    val latitude: Double,
    val longitude: Double
) : Serializable