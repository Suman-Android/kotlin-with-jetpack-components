package com.amadeus.myapplication.common.footer

import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.amadeus.myapplication.databinding.ItemPagingFooterBinding
import com.amadeus.myapplication.common.footer.FooterUiState
import com.amadeus.myapplication.utils.executeWithAction

/**
 * Created by Suman Singh on 7/7/2022.
 */
class FooterViewHolder(
    private val binding: ItemPagingFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.executeWithAction {
            footerUiState = FooterUiState(loadState)
        }
    }
}
