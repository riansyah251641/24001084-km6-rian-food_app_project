package com.fromryan.projectfoodapp.presentation.home.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.R.*
import com.fromryan.projectfoodapp.data.model.Catalog
import com.fromryan.projectfoodapp.data.model.Category
import com.fromryan.projectfoodapp.databinding.ItemFoodListcategoryBinding

class CategoryAdapter(private val itemCatalog: (Category) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var CategoryGroup = RecyclerView.NO_POSITION

    private val asyncDataDiffer = AsyncListDiffer<Category>(
        this, object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }
        }
    )
    fun submitData(items: List<Category>) {
        asyncDataDiffer.submitList(items)
    }

    inner class CategoryViewHolder(private val binding: ItemFoodListcategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val adapterPosition = adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    itemCatalog(asyncDataDiffer.currentList[adapterPosition])
                    notifyItemChanged(CategoryGroup)
                    CategoryGroup = adapterPosition
                    notifyItemChanged(CategoryGroup)
                }
            }
        }

        fun bind(item: Category) {
            binding.tvCategoryName.text = item.name
            binding.ivCategoryImage.load(item.image) {
                error(drawable.ic_launcher_background)
            }
            if (adapterPosition == CategoryGroup) {
                binding.tvCategoryName.setBackgroundResource(drawable.bg_rectangle_yellow_16)
                binding.tvCategoryName.setTextColor(Color.WHITE)
            } else {
                binding.tvCategoryName.setBackgroundColor(Color.TRANSPARENT)
                binding.tvCategoryName.setTextColor(Color.BLACK)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemFoodListcategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(asyncDataDiffer.currentList[position])
    }
}