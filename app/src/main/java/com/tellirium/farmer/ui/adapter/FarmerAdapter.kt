package com.tellirium.farmer.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tellirium.farmer.R
import com.tellirium.farmer.db.model.Farm
import com.tellirium.farmer.db.model.Farmer
import com.tellirium.farmer.util.Constants
import kotlinx.android.synthetic.main.farmer_list_item.view.*

class FarmerAdapter :  RecyclerView.Adapter<FarmerAdapter.FarmerViewHolder>()  {
    inner class FarmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    val diffCallback = object : DiffUtil.ItemCallback<Farmer>() {
        override fun areItemsTheSame(oldItem: Farmer, newItem: Farmer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Farmer, newItem: Farmer): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Farmer>) = differ.submitList(list)


    private var onItemClickListener: ((Farmer) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmerViewHolder {
        return FarmerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.farmer_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FarmerViewHolder, position: Int) {
        val farmer = differ.currentList[position]
        holder.itemView.apply {
            Picasso.get().load(Constants.BASE_IMAGE_URL + farmer.passport_photo).fit().centerCrop()
                .into(imageView)
            tvFarmerName.text = farmer.first_name + " " + farmer.surname
            tvFarmerAge.text = farmer.dob
            setOnClickListener {
                onItemClickListener?.let { it(farmer) }
            }
        }

    }

    fun setOnItemClickListener(listener: (Farmer) -> Unit) {
        onItemClickListener = listener
    }
}