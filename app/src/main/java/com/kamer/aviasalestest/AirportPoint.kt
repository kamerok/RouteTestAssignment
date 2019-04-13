package com.kamer.aviasalestest

import java.io.Serializable


data class AirportPoint(
    val shortName: String,
    val latitude: Double,
    val longitude: Double
) : Serializable