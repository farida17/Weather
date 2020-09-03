package com.farida.kotlin_api_weather.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.farida.kotlin_api_weather.R
import com.farida.kotlin_api_weather.di.WeatherApplication
import kotlinx.android.synthetic.main.activity_weather.*
import javax.inject.Inject

class WeatherActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: WeatherViewModelFactory
    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        setSupportActionBar(toolbar as Toolbar?)
        (application as WeatherApplication).applicationComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = CurrentWeatherFragment()
        fragmentTransaction.add(R.id.current_fragment, fragment)
        fragmentTransaction.commit()
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        
        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(cityName: String?): Boolean {
                if (cityName != null) {
                    viewModel.loadCurrentWeather(cityName)
                    viewModel.loadForecast(cityName)
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.menu_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            setIconifiedByDefault(false)
        }
        return true
    }
}
