package com.fromryan.projectfoodapp.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.base.ViewHolderBinder
import com.fromryan.projectfoodapp.data.model.Catalog
import com.fromryan.projectfoodapp.databinding.ItemFoodGridBinding
import java.text.NumberFormat
import java.util.Locale

class FoodListGridView (
    private val binding: ItemFoodGridBinding,
    private val listener: OnItemClickedListener<Catalog>
    ): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Catalog> {

        fun Double.formatToIDRCurrency(): String {
            val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            return formatter.format(this)
        }
        override fun bind(item: Catalog) {
            item.let {
                binding.ivFoodPhoto.load(it.image){
                    crossfade(true)
                    error(R.mipmap.ic_launcher)
                }
                binding.tvFoodName.text = it.name
                binding.tvFoodPrice.text = it.price.formatToIDRCurrency()
                itemView.setOnClickListener {
                    listener.onItemClicked(item)
                }
            }
        }


    }