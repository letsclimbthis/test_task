package com.yaroslavm.cft.ui.request

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yaroslavm.cft.BinRequest
import com.yaroslavm.cft.databinding.BinInfoRequestFragmentItemHistoryBinding

class AdapterSearchHistory(
    private val itemList: List<BinRequest>,
    private val outerClickHandler: OnHistoryListItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    interface OnHistoryListItemClickListener {
        fun onItemListClick(viewClicked: View, itemIndexInList: Int)
    }

    inner class SearchHistoryItemViewHolder(binding: BinInfoRequestFragmentItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        override fun onClick(p0: View?) {
            p0?.let { outerClickHandler.onItemListClick(p0, adapterPosition) }
        }
    }

    private lateinit var binding: BinInfoRequestFragmentItemHistoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = BinInfoRequestFragmentItemHistoryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHistoryItemViewHolder(binding)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(binding) {
            with(itemList[position]) { binding.binEntity = this }
            clickHandler = holder as SearchHistoryItemViewHolder
        }
    }

}