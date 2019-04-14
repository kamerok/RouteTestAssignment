package com.kamer.aviasalestest.features.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.kamer.aviasalestest.features.progress.ProgressActivity
import com.kamer.aviasalestest.R
import com.kamer.aviasalestest.features.select.SelectCityActivity
import com.kamer.aviasalestest.model.City
import com.kamer.aviasalestest.utils.setupRx
import com.kamer.aviasalestest.app.App
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainRouter {

    private val viewModel: MainViewModel by lazy { App.serviceLocator.buildMainViewModel(this) }

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
        startActivity(ProgressActivity.intent(this, origin, destination))
    }

    override fun showSelectCity(isOrigin: Boolean) {
        startActivity(SelectCityActivity.intent(this, isOrigin))
    }

    private fun updateViews(state: MainUiModel) {
        originNameView.text = state.originName
        originIataView.text = state.originIata
        destinationNameView.text = state.destinationName
        destinationIataView.text = state.destinationIata
    }
}
