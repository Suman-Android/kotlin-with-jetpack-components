package com.amadeus.myapplication.common.footer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.amadeus.myapplication.R
import com.amadeus.myapplication.databinding.ItemPagingFooterBinding


/**
 * Created by Suman Singh on 7/7/2021.
 */

class FooterAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<FooterViewHolder>() {
    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FooterViewHolder {
        val itemPagingFooterBinding = inflate<ItemPagingFooterBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_paging_footer,
            parent,
            false
        )
        return FooterViewHolder(itemPagingFooterBinding, retry)
    }

}
