package com.farida.kotlin_api_weather.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import butterknife.BindView
import butterknife.ButterKnife
import com.farida.kotlin_api_weather.R
import com.farida.kotlin_api_weather.di.WeatherApplication
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.activity_weather.view.*
import javax.inject.Inject

class WeatherActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: WeatherViewModelFactory
    private lateinit var viewModel: WeatherViewModel

    @JvmField
    @BindView(R.id.toolbar)
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        viewPager2.adapter =
            ViewPagerAdapter(fragmentManager = supportFragmentManager, lifecycle = lifecycle)
        TabLayoutMediator(tabs, viewPager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                if (position == 0) {
                    tab.text = "CURRENT WEATHER"
                } else {
                    tab.text = "5 DAYS FORECAST"
                }
            }).attach()
        (application as WeatherApplication).applicationComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)

        viewPager2.setPageTransformer(ZoomOutPageTransformer())

//        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                viewModel.getLiveProgress()
//            }
//        })
    }
}

