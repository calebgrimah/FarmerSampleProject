package com.tellirium.farmer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tellirium.farmer.R
import com.tellirium.farmer.db.model.Farm
import kotlinx.android.synthetic.main.farm_list_item.view.*

class FarmAdapter : RecyclerView.Adapter<FarmAdapter.FarmViewHolder>() {
    inner class FarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val diffCallback = object : DiffUtil.ItemCallback<Farm>() {
        override fun areItemsTheSame(oldItem: Farm, newItem: Farm): Boolean {
            return oldItem.farmId == newItem.farmId
        }

        override fun areContentsTheSame(oldItem: Farm, newItem: Farm): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Farm>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmViewHolder {
        return FarmViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.farm_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FarmViewHolder, position: Int) {
        val farm = differ.currentList[position]
        holder.itemView.apply {
            etFarmName.text = farm.farmName
            tvLocation.text = farm.farmLocation
        }
    }
}