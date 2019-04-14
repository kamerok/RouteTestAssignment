package com.kamer.aviasalestest.features.select

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.kamer.aviasalestest.R
import com.kamer.aviasalestest.dependency.ServiceLocator
import com.kamer.aviasalestest.utils.setupRx
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_select_city.*


class SelectCityActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_IS_ORIGIN = "is_origin"

        fun intent(context: Context, isOrigin: Boolean): Intent =
            Intent(context, SelectCityActivity::class.java).apply {
                putExtra(EXTRA_IS_ORIGIN, isOrigin)
            }

    }

    private val viewModel: SelectCityViewModel by lazy {
        ServiceLocator.buildSelectCityViewModel(
            this,
            intent.getBooleanExtra(EXTRA_IS_ORIGIN, true)
        )
    }
    private val adapter: SelectCityAdapter by lazy {
        SelectCityAdapter { city -> viewModel.postEvent(CitySelected(city)) }
    }

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
        setContentView(R.layout.activity_select_city)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun updateViews(state: SelectCityUiModel) {
        queryView.hint = state.hint
//        queryView.setText(state.query)
        adapter.setData(state.items)
    }

}