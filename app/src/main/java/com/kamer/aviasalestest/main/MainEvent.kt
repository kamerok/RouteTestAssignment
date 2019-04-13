package com.kamer.aviasalestest.main


sealed class MainEvent

object SelectOrigin : MainEvent()

object SelectDestination : MainEvent()

object Search : MainEvent()