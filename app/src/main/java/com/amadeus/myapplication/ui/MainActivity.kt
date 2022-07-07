package com.amadeus.myapplication.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.amadeus.myapplication.R
import com.amadeus.myapplication.common.footer.FooterAdapter
import com.amadeus.myapplication.databinding.ActivityMainBinding
import com.amadeus.myapplication.models.WeatherUiState
import com.amadeus.myapplication.models.WeatherItemUiState
import com.amadeus.myapplication.paging.WeatherPagingAdapter
import com.amadeus.myapplication.utils.collect
import com.amadeus.myapplication.utils.collectLast
import com.amadeus.myapplication.utils.executeWithAction
import com.amadeus.myapplication.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var weatherPagingAdapter: WeatherPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val inputStream: InputStream = assets.open("weather_14.json")
        mainViewModel.readDataFromFile(inputStream)
        setBinding()
        setAdapter()
        setListener()
        collectLast(mainViewModel.weatherItemsUiStates, ::setUsers)
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun setListener() {
        binding.btnRetry.setOnClickListener { weatherPagingAdapter.retry() }
    }

    private fun setAdapter() {
        collect(flow = weatherPagingAdapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.refresh },
            action = ::setUsersUiState
        )
        binding.rvWeatherList.adapter =
            weatherPagingAdapter.withLoadStateFooter(FooterAdapter(weatherPagingAdapter::retry))
    }

    private fun setUsersUiState(loadState: LoadState) {
        binding.executeWithAction {
            usersUiState = WeatherUiState(loadState)
        }
    }

    private suspend fun setUsers(userItemsPagingData: PagingData<WeatherItemUiState>) {
        weatherPagingAdapter.submitData(userItemsPagingData)
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
