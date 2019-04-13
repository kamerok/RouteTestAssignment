package com.kamer.aviasalestest.features.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kamer.aviasalestest.AirportPoint
import com.kamer.aviasalestest.ProgressActivity
import com.kamer.aviasalestest.R
import com.kamer.aviasalestest.dependency.ServiceLocator
import com.kamer.aviasalestest.model.City
import com.kamer.aviasalestest.utils.setupRx
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainRouter {

    private val viewModel: MainViewModel by lazy { ServiceLocator.buildMainViewModel(this) }

    init {
        setupRx { compositeDisposable ->
            viewModel.state
                .subscribe(
                    { state -> updateViews(state) },
                    { Log.e("Main", "error", it) }
                )
                .addTo(compositeDisposable)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        originView.setOnClickListener { viewModel.postEvent(SelectOrigin) }
        destinationView.setOnClickListener { viewModel.postEvent(SelectDestination) }
        searchView.setOnClickListener { viewModel.postEvent(Search) }
    }

    override fun showProgress(origin: City, destination: City) {
        startActivity(
            ProgressActivity.intent(
                this,
                AirportPoint(origin.iata, origin.latitude, origin.longitude),
                AirportPoint(destination.iata, destination.latitude, destination.longitude)
            )
        )
    }

    override fun showSelectCity(isSource: Boolean) {
        TODO("not implemented")
    }

    private fun updateViews(state: MainUiModel) {
        originNameView.text = state.fromName
        originIataView.text = state.fromIata
        destinationNameView.text = state.toName
        destinationIataView.text = state.toIata
    }
}
