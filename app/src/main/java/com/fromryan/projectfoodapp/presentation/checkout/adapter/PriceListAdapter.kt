import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fromryan.projectfoodapp.data.model.PriceItem
import com.fromryan.projectfoodapp.databinding.ItemCheckoutFoodListBinding
import com.fromryan.projectfoodapp.utils.formatToIDRCurrency

class PriceListAdapter(private val itemClick: (PriceItem) -> Unit) :
    RecyclerView.Adapter<PriceListAdapter.PriceItemViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<PriceItem>() {
                override fun areItemsTheSame(
                    oldItem: PriceItem,
                    newItem: PriceItem
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: PriceItem,
                    newItem: PriceItem
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            }
        )

    fun submitData(data: List<PriceItem>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceItemViewHolder {
        val binding = ItemCheckoutFoodListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PriceItemViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: PriceItemViewHolder, position: Int) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size


    class PriceItemViewHolder(
        private val binding: ItemCheckoutFoodListBinding,
        val itemClick: (PriceItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: PriceItem) {
            with(item) {
                binding.tvItemPrice.text = item.total.formatToIDRCurrency()
                binding.tvItemTitle.text = item.name
                itemView.setOnClickListener { itemClick(this) }
            }

        }
    }

}