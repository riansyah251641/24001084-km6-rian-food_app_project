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
import com.fromryan.projectfoodapp.databinding.ItemCartCatalogBinding
import com.fromryan.projectfoodapp.databinding.ItemCartCatalogOrderBinding
import com.fromryan.projectfoodapp.utils.doneEditing
import com.fromryan.projectfoodapp.utils.formatToIDRCurrency

class CartListAdapter(private val cartListener: CartListener? = null) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val dataDiffer =
            AsyncListDiffer(this, object : DiffUtil.ItemCallback<Cart>() {
                override fun areItemsTheSame(
                    oldItem: Cart,
                    newItem: Cart
                ): Boolean {
                    return oldItem.id == newItem.id && oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Cart,
                    newItem: Cart
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            })

    fun submitData(data: List<Cart>) {
        dataDiffer.submitList(data)
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
            (holder as ViewHolderBinder<Cart>).bind(dataDiffer.currentList[position])
        }

    }

    class CartViewHolder(
        private val binding: ItemCartCatalogBinding,
        private val cartListener: CartListener?
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
        override fun bind(item: Cart) {
            setCartData(item)
            setCartNotes(item)
            setClickListeners(item)
        }

        private fun setCartData(item: Cart) {
            with(binding) {
                binding.ivCatalogImage.load(item.catalogImgUrl) {
                    crossfade(true)
                }
                tvCatalogCount.text = item.itemQuantity.toString()
                tvCatalogName.text = item.catalogName
                tvCatalogPrice.text = (item.itemQuantity * item.catalogPrice).formatToIDRCurrency()
            }
        }

        private fun setCartNotes(item: Cart) {
            binding.etNotesItem.setText(item.itemNotes)
            binding.etNotesItem.doneEditing {
                binding.etNotesItem.clearFocus()
                val newItem = item.copy().apply {
                    itemNotes = binding.etNotesItem.text.toString().trim()
                }
                cartListener?.onUserDoneEditingNotes(newItem)
            }
        }

        private fun setClickListeners(item: Cart) {
            with(binding) {
                ivMinus.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item) }
                ivPlus.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item) }
                ivRemoveCart.setOnClickListener { cartListener?.onRemoveCartClicked(item) }
            }
        }
    }

    class CartOrderViewHolder(
        private val binding: ItemCartCatalogOrderBinding,
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
        override fun bind(item: Cart) {
            setCartData(item)
            setCartNotes(item)
        }

        private fun setCartData(item: Cart) {
            with(binding) {
                binding.ivProductImage.load(item.catalogImgUrl) {
                    crossfade(true)
                }
                tvTotalQuantity.text =
                    itemView.rootView.context.getString(
                        R.string.total_quantity,
                        item.itemQuantity.toString()
                    )
                tvProductName.text = item.catalogName
                tvProductPrice.text = (item.itemQuantity * item.catalogPrice).toString()
            }
        }

        private fun setCartNotes(item: Cart) {
            binding.tvNotes.text = item.itemNotes
        }

    }


    interface CartListener {
        fun onPlusTotalItemCartClicked(cart: Cart)
        fun onMinusTotalItemCartClicked(cart: Cart)
        fun onRemoveCartClicked(cart: Cart)
        fun onUserDoneEditingNotes(cart: Cart)
    }