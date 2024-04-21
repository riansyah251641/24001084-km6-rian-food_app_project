package com.fromryan.projectfoodapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.base.ViewHolderBinder
import com.fromryan.projectfoodapp.data.model.Catalog
import com.fromryan.projectfoodapp.databinding.ItemFoodGridBinding
import com.fromryan.projectfoodapp.databinding.ItemFoodLinierBinding

class CatalogAdapter (
    private val listener: OnItemClickedListener<Catalog>,
    private val listMode: Int = MODE_LIST
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val MODE_LIST = 0
        const val MODE_GRID = 1
    }

    private var asyncDataDiffer = AsyncListDiffer(
        this, object : DiffUtil.ItemCallback<Catalog>() {
            override fun areItemsTheSame(oldItem: Catalog, newItem: Catalog): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Catalog, newItem: Catalog): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    )

    fun submitData(data: List<Catalog>) {
        asyncDataDiffer.submitList(data)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //membuat instance of view holder
        return if (listMode == MODE_GRID) FoodListGridView(
            ItemFoodGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        ) else {
            FoodListLinierView(
                ItemFoodLinierBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), listener
            )
        }
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ViewHolderBinder<*>) return
        (holder as ViewHolderBinder<Catalog>).bind(asyncDataDiffer.currentList[position])
        holder.itemView.findViewById<ImageView>(R.id.iv_store_icon).setOnClickListener {
            Toast.makeText(holder.itemView.context, "Berhasil Ditambahkan ke Cart", Toast.LENGTH_SHORT).show()
            val item = asyncDataDiffer.currentList[position]
            listener.onItemAddedToCart(item)
        }
    }


}

interface OnItemClickedListener<T> {
    fun onItemClicked(item: T)
    fun onItemAddedToCart(item: T)
}



