package com.cursosandroidant.weather.mainModule.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cursosandroidant.weather.BR
import com.cursosandroidant.weather.R
import com.cursosandroidant.weather.common.entities.Forecast
import com.cursosandroidant.weather.common.utils.CommonUtils
import com.cursosandroidant.weather.databinding.ActivityMainBinding
import com.cursosandroidant.weather.mainModule.view.adapters.ForecastAdapter
import com.cursosandroidant.weather.mainModule.view.adapters.OnClickListener
import com.cursosandroidant.weather.mainModule.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ForecastAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //con estas ya queda configurada la peticion:
        setupViewModel()
        setupObservers()

        //databinding + recyclerView
        setupAdapter()
        setupRecyclerView()
    }

    private fun setupViewModel() {
        val vm: MainViewModel by viewModels()
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, vm)
    }

    private fun setupObservers() {
        binding.viewModel?.let {
            it.getSnackbarMsg().observe(this){ resMsg ->
                Snackbar.make(binding.root, resMsg, Snackbar.LENGTH_LONG).show()
            }
            it.getResult().observe(this){ result ->
                adapter.submitList(result.hourly)
            }
        }
    }

    private fun setupAdapter(){
        adapter = ForecastAdapter(this)
    }

    private fun setupRecyclerView(){
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    //al inicio de la app, se lanzara la peticion a la API
    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            binding.viewModel?.getWeatherAndForecast (-31.4413, -64.1811,
                "09ed2cfc331705f6fd102606ed441669", "", "metric", "es")
        }
    }

    /*
    * OnClickListener
    * */
    override fun onClick(forecast: Forecast) {
        Snackbar.make(binding.root, CommonUtils.getFullDate(forecast.dt), Snackbar.LENGTH_LONG).show()
    }
}