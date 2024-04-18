package com.fromryan.projectfoodapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.model.Category
import com.fromryan.projectfoodapp.databinding.ItemFoodListcategoryBinding

class CategoryAdapter(private val itemClick: (Category) -> Unit)  : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private val data = mutableListOf<Category>()

    fun submitData(items : List<Category>){
        data.addAll(items)
    }

    class CategoryViewHolder(private val binding: ItemFoodListcategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.tvCategoryName.text = item.name
            binding.ivCategoryImage.load(item.image) {
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemFoodListcategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(data[position])
    }
}