package com.kamer.aviasalestest.features.main


sealed class MainEvent

object SelectOrigin : MainEvent()

object SelectDestination : MainEvent()

object Search : MainEvent()