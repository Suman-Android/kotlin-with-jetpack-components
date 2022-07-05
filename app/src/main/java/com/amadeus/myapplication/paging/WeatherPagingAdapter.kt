package com.amadeus.myapplication.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amadeus.myapplication.R
import com.amadeus.myapplication.databinding.ItemCityLayoutBinding
import com.amadeus.myapplication.models.WeatherDataItem
import com.amadeus.myapplication.utils.Utility
import androidx.databinding.ViewDataBinding
import javax.inject.Inject

class WeatherPagingAdapter @Inject constructor() :
    PagingDataAdapter<WeatherDataItem, WeatherPagingAdapter.WeatherViewHolder>(COMPARATOR) {

    class WeatherViewHolder(private val binding: ItemCityLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherDataItem: WeatherDataItem) {
            binding.apply {
                textViewCity.text =
                    weatherDataItem.city?.name + ", " + weatherDataItem.city?.country
                textViewUpdatedAt.text =
                    "Updated at " + Utility.convertLongToTime(weatherDataItem.time?.toLong())
                textViewStatus.text = weatherDataItem?.weather?.get(0)?.main?.toString()
                textViewTemp.text = weatherDataItem?.main?.temp.toString()
                textViewMinTemp.text = "Min Temp:" + weatherDataItem?.main?.temp_min.toString()
                textViewMaxtTemp.text = "Max Temp:" + weatherDataItem?.main?.temp_max.toString()
                textViewSeaLevel.text =
                    "Seal Level \n" + weatherDataItem?.main?.sea_level.toString()
                textviewGroundLevel.text =
                    "Pressure \n" + weatherDataItem?.main?.grnd_level.toString()
            }
        }

    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding =
            ItemCityLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)

    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<WeatherDataItem>() {
            override fun areItemsTheSame(
                oldItem: WeatherDataItem, newItem: WeatherDataItem
            ): Boolean {
                return oldItem?.city?.id == newItem?.city?.id
            }

            override fun areContentsTheSame(
                oldItem: WeatherDataItem, newItem: WeatherDataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun <T : ViewDataBinding> T.executeWithAction(action: T.() -> Unit) {
        action()
        executePendingBindings()
    }
}




















