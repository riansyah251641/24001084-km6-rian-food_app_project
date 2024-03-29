package com.fromryan.projectfoodapp.presentation.detailfood

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.datasource.cart.CartDataSource
import com.fromryan.projectfoodapp.data.datasource.cart.CartDatabaseDataSource
import com.fromryan.projectfoodapp.data.model.Catalog
import com.fromryan.projectfoodapp.data.repository.CartRepository
import com.fromryan.projectfoodapp.data.repository.CartRepositoryImpl
import com.fromryan.projectfoodapp.data.source.lokal.database.AppDatabase
import com.fromryan.projectfoodapp.databinding.ActivityDetailFoodBinding
import com.fromryan.projectfoodapp.utils.GenericViewModelFactory
import com.fromryan.projectfoodapp.utils.formatToIDRCurrency
import com.fromryan.projectfoodapp.utils.proceedWhen

class DetailFoodActivity : AppCompatActivity() {

companion object {
    const val EXTRA_CATALOG = "EXTRA_CATALOG"
    const val EXTRAS_ITEM = "EXTRAS_ITEM"
    fun startActivity(context: Context, foodData: Catalog) {
        val intent = Intent(context, DetailFoodActivity::class.java)
        intent.putExtra(EXTRAS_ITEM, foodData)
        intent.putExtra(EXTRA_CATALOG, foodData)
        context.startActivity((intent))
    }
}
private var count = 1
private var priceItem: Double = 0.00
private var linkToMaps: String = ""


private val binding: ActivityDetailFoodBinding by lazy {
    ActivityDetailFoodBinding.inflate(layoutInflater)
}

    private val viewModel: DetailFoodViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(
            DetailFoodViewModel(intent?.extras, rp)
        )
    }

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    binding.tvCountOrder.text = count.toString()
    getArgumentData()
    clickAction()
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
    binding.btnDetailOrder.text= item.price.formatToIDRCurrency()
    priceItem = item.price
    linkToMaps = item.location
    binding.ivBannerDetail.load(item.image) {
        crossfade(true)
        error(R.mipmap.ic_launcher)
    }
}

    private fun addProductToCart() {
        viewModel.addToCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_add_to_cart_success), Toast.LENGTH_SHORT
                    ).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(this, getString(R.string.add_to_cart_failed), Toast.LENGTH_SHORT)
                        .show()
                },
                doOnLoading = {
                    Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

private fun clickAction(){
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

    binding.buttonCartSubmit.setOnClickListener {
        addProductToCart()
    }

    binding.ivOrderUp.setOnClickListener {
        count += 1
        binding.tvCountOrder.text = count.toString()
        updateBtnDetailOrderText(priceItem)
        viewModel.add()
    }
    binding.ivOrderDown.setOnClickListener {
        if (count > 0){
            viewModel.minus()
            binding.tvCountOrder.text = count.toString()
            updateBtnDetailOrderText(priceItem)
            count -= 1
        }else{
            Toast.makeText(this, "minimal 1 cuyy", Toast.LENGTH_SHORT).show()
        }
    }

    binding.ivButtonBack.setOnClickListener {
        onBackPressed()
    }

}

private fun updateBtnDetailOrderText(price: Double) {
    val totalPrice = price * count
    binding.btnDetailOrder.text = "${totalPrice.formatToIDRCurrency()}"
}

}