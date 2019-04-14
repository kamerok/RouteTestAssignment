package com.kamer.aviasalestest.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.kamer.aviasalestest.model.City
import io.reactivex.Completable
import io.reactivex.Observable


class SelectedCityStorage(
    context: Context,
    private val gson: Gson
) {

    companion object {
        private const val KEY_ORIGIN = "origin"
        private const val KEY_DESTINATION = "destination"
    }

    private val prefs = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun getOrigin(): Observable<City> = createObserver(KEY_ORIGIN) {
        getOriginInternal()
    }

    fun getDestination(): Observable<City> = createObserver(KEY_DESTINATION) {
        getDestinationInternal()
    }

    fun setOrigin(city: City): Completable = Completable.fromAction {
        val currentOrigin = getOriginInternal()
        val destination = getDestinationInternal()
        //in real project this logic should be in domain logic layer
        if (destination.id == city.id) {
            setDestinationInternal(currentOrigin)
        }
        setOriginInternal(city)
    }

    fun setDestination(city: City): Completable = Completable.fromAction {
        val currentDestination = getDestinationInternal()
        val origin = getOriginInternal()
        //in real project this logic should be in domain logic layer
        if (origin.id == city.id) {
            setOrigin(currentDestination)
        }
        setDestinationInternal(city)
    }

    private fun getOriginInternal(): City =
        prefs.getString(KEY_ORIGIN, null)?.let { gson.fromJson(it, City::class.java) } ?: hardcodedOrigin()

    private fun getDestinationInternal(): City =
        prefs.getString(KEY_DESTINATION, null)?.let { gson.fromJson(it, City::class.java) } ?: hardcodedDestination()

    private fun setOriginInternal(city: City) {
        prefs.edit().putString(KEY_ORIGIN, gson.toJson(city)).apply()
    }

    private fun setDestinationInternal(city: City) {
        prefs.edit().putString(KEY_DESTINATION, gson.toJson(city)).apply()
    }

    private fun <T> createObserver(key: String, retriever: () -> T): Observable<T> =
        Observable
            .create<T> { e ->
                e.onNext(retriever())
                val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
                    if (!e.isDisposed && changedKey == key) {
                        e.onNext(retriever())
                    }
                }
                prefs.registerOnSharedPreferenceChangeListener(listener)
                e.setCancellable { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
            }

    private fun hardcodedOrigin() = City(12153, "Москва", "MOW", 55.752041, 37.617508)

    private fun hardcodedDestination() = City(20857, "Нью-Йорк", "NYC", 40.75603, -73.986956)

}