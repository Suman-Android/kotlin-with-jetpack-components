package com.amadeus.myapplication.ui

import android.os.Bundle
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amadeus.myapplication.R
import com.amadeus.myapplication.paging.WeatherPagingAdapter
import com.amadeus.myapplication.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var weatherPagingAdapter: WeatherPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val inputStream: InputStream = assets.open("weather_14.json")
        val rvWeatherList = findViewById<RecyclerView>(R.id.rvWeatherList)
        val progress = findViewById<ProgressBar>(R.id.progressBar)

        rvWeatherList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = weatherPagingAdapter
        }

        mainViewModel.readDataFromFile(inputStream)

        lifecycleScope.launch {
            mainViewModel.newWeatherList.collect {
                progress.visibility = GONE
                rvWeatherList.visibility = VISIBLE
                weatherPagingAdapter.submitData(it)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_weather, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)
                    mainViewModel.onSubmitQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null)
                    mainViewModel.onSubmitQuery(newText)
                return true
            }
        })

        return true
    }
}
