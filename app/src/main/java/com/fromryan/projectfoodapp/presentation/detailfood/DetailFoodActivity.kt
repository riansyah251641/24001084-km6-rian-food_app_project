package com.fromryan.projectfoodapp.presentation.detailfood

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.model.Catalog
import com.fromryan.projectfoodapp.databinding.ActivityCheckoutBinding
import com.fromryan.projectfoodapp.databinding.ActivityDetailFoodBinding
import java.text.NumberFormat
import java.util.Locale

class DetailFoodActivity : AppCompatActivity() {

companion object {
    const val EXTRAS_ITEM = "EXTRAS_ITEM"
    fun startActivity(context: Context, foodData: Catalog) {
        val intent = Intent(context, DetailFoodActivity::class.java)
        intent.putExtra(EXTRAS_ITEM, foodData)
        context.startActivity((intent))
    }
}
private var count = 0
private var priceItem: Double = 0.00
private var linkToMaps: String = ""
private val binding: ActivityDetailFoodBinding by lazy {
    ActivityDetailFoodBinding.inflate(layoutInflater)
}

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    getArgumentData()
    clickCountOrder()
    clickToMaps()
    clickToBack()
}

private fun getArgumentData() {
    intent.extras?.getParcelable <Catalog>(EXTRAS_ITEM)?. let{
        setProfileData(it)
    }
}

private fun setProfileData(item: Catalog) {
    binding.tvName.text = item.name
    binding.tvDeskripsi.text = item.description
    binding.tvPrice.text=item.price.formatToIDRCurrency()
    binding.tvIsiLokasi.text=item.location
    binding.btnDetailOrder.text= "Tambah ke Keranjang -> " +item.price.formatToIDRCurrency()
    priceItem = item.price
    linkToMaps = item.location
    binding.ivBannerDetail.load(item.image) {
        crossfade(true)
        error(R.mipmap.ic_launcher)
    }
}

private fun clickCountOrder(){
    binding.ivOrderUp.setOnClickListener {
        count += 1
        binding.tvCountOrder.text = count.toString()
        updateBtnDetailOrderText(priceItem)
    }
    binding.ivOrderDown.setOnClickListener {
        if (count > 0){
            binding.tvCountOrder.text = count.toString()
            updateBtnDetailOrderText(priceItem)
            count -= 1
        }else{
            Toast.makeText(this, "minimal 1 cuyy", Toast.LENGTH_SHORT).show()
        }

    }
}

private fun clickToBack(){
    binding.ivButtonBack.setOnClickListener {
        onBackPressed()
    }
}

private fun clickToMaps(){
    binding.tvIsiLokasi.setOnClickListener {
        val location =  linkToMaps
        val intentUri = Uri.parse("geo:0,0?q=$location")
        val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
    binding.btnDetailOrder.setOnClickListener {
        val location =  linkToMaps
        val intentUri = Uri.parse("geo:0,0?q=$location")
        val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

}


fun Double.formatToIDRCurrency(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return formatter.format(this)
}

private fun updateBtnDetailOrderText(price: Double) {
    val totalPrice = price * count
    binding.btnDetailOrder.text = "Tambah ke Keranjang -> ${totalPrice.formatToIDRCurrency()}"
}
}