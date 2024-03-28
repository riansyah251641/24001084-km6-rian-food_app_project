package com.fromryan.projectfoodapp.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.base.ViewHolderBinder
import com.fromryan.projectfoodapp.data.model.Cart
import com.fromryan.projectfoodapp.data.model.CartCatalog
import com.fromryan.projectfoodapp.databinding.ItemCartCatalogBinding
import com.fromryan.projectfoodapp.databinding.ItemCartCatalogOrderBinding
import com.fromryan.projectfoodapp.utils.doneEditing

class CartListAdapter(private val cartListener: CartListener? = null) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val dataDiffer =
            AsyncListDiffer(this, object : DiffUtil.ItemCallback<CartCatalog>() {
                override fun areItemsTheSame(
                    oldItem: CartCatalog,
                    newItem: CartCatalog
                ): Boolean {
                    return oldItem.cart.id == newItem.cart.id && oldItem.catalog.id == newItem.catalog.id
                }

                override fun areContentsTheSame(
                    oldItem: CartCatalog,
                    newItem: CartCatalog
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            })

        fun submitData(data: List<Cart>) {
//            dataDiffer.submitList(data)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return if (cartListener != null) CartViewHolder(
                ItemCartCatalogBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), cartListener
            ) else CartOrderViewHolder(
                ItemCartCatalogOrderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        override fun getItemCount(): Int = dataDiffer.currentList.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ViewHolderBinder<CartCatalog>).bind(dataDiffer.currentList[position])
        }

    }

    class CartViewHolder(
        private val binding: ItemCartCatalogBinding,
        private val cartListener: CartListener?
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartCatalog> {
        override fun bind(item: CartCatalog) {
            setCartData(item)
            setCartNotes(item)
            setClickListeners(item)
        }

        private fun setCartData(item: CartCatalog) {
            with(binding) {
                binding.ivCatalogImage.load(item.catalog.image) {
                    crossfade(true)
                }
                tvCatalogCount.text = item.cart.itemQuantity.toString()
                tvCatalogName.text = item.catalog.name
                tvCatalogPrice.text = (item.cart.itemQuantity * item.catalog.price).toString()
            }
        }

        private fun setCartNotes(item: CartCatalog) {
            binding.etNotesItem.setText(item.cart.itemNotes)
            binding.etNotesItem.doneEditing {
                binding.etNotesItem.clearFocus()
                val newItem = item.cart.copy().apply {
                    itemNotes = binding.etNotesItem.text.toString().trim()
                }
                cartListener?.onUserDoneEditingNotes(newItem)
            }
        }

        private fun setClickListeners(item: CartCatalog) {
            with(binding) {
                ivMinus.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item.cart) }
                ivPlus.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item.cart) }
                ivRemoveCart.setOnClickListener { cartListener?.onRemoveCartClicked(item.cart) }
            }
        }
    }

    class CartOrderViewHolder(
        private val binding: ItemCartCatalogOrderBinding,
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartCatalog> {
        override fun bind(item: CartCatalog) {
            setCartData(item)
            setCartNotes(item)
        }

        private fun setCartData(item: CartCatalog) {
            with(binding) {
                binding.ivProductImage.load(item.catalog.image) {
                    crossfade(true)
                }
                tvTotalQuantity.text =
                    itemView.rootView.context.getString(
                        R.string.total_quantity,
                        item.cart.itemQuantity.toString()
                    )
                tvProductName.text = item.catalog.name
                tvProductPrice.text = (item.cart.itemQuantity * item.catalog.price).toString()
            }
        }

        private fun setCartNotes(item: CartCatalog) {
            binding.tvNotes.text = item.cart.itemNotes
        }

    }


    interface CartListener {
        fun onPlusTotalItemCartClicked(cart: Cart)
        fun onMinusTotalItemCartClicked(cart: Cart)
        fun onRemoveCartClicked(cart: Cart)
        fun onUserDoneEditingNotes(cart: Cart)
    }