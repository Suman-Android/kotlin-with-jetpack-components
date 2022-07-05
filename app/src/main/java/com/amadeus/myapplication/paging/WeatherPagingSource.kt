//package com.amadeus.myapplication.paging
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import androidx.paging.RemoteMediator
//import com.amadeus.myapplication.db.WeatherForecastDao
//import com.amadeus.myapplication.models.WeatherDataItem
//import com.amadeus.myapplication.repository.WeatherForecastRepository
//import retrofit2.HttpException
//import java.io.IOException
//import java.lang.Exception
//
//const val STARTING_INDEX = 1
//
//class WeatherPagingSource(
//    private val weatherForecastRepository: WeatherForecastRepository,
//    private val weatherForecastDao: WeatherForecastDao
//) : PagingSource<Int, WeatherDataItem>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WeatherDataItem> {
//        val position = params.key ?: STARTING_INDEX
//
//        return try {
//            val data = weatherForecastRepository.getWeatherList()
//
//            LoadResult.Page(
//                data = data,
//                prevKey = if (params.key== STARTING_INDEX) null else position - 1,
//                nextKey = if (data.isEmpty()) null else position + 1
//            )
//        } catch (e: IOException) {
//            LoadResult.Error(e)
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, WeatherDataItem>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//}