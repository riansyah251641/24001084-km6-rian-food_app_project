package com.fromryan.projectfoodapp.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.datasource.DummyCatalogDataSource
import com.fromryan.projectfoodapp.data.datasource.DummyCategoryDataSource
import com.fromryan.projectfoodapp.data.model.Catalog
import com.fromryan.projectfoodapp.data.repository.CatalogRepository
import com.fromryan.projectfoodapp.data.repository.CatalogRepositoryImpl
import com.fromryan.projectfoodapp.data.repository.CategoryRepository
import com.fromryan.projectfoodapp.data.repository.CategoryRepositoryImpl
import com.fromryan.projectfoodapp.databinding.FragmentHomeBinding
import com.fromryan.projectfoodapp.presentation.detailfood.DetailFoodActivity
import com.fromryan.projectfoodapp.presentation.home.adapter.CategoryAdapter
import com.fromryan.projectfoodapp.presentation.home.adapter.FoodListAdapter
import com.fromryan.projectfoodapp.presentation.home.adapter.OnItemClickedListener
import com.fromryan.projectfoodapp.utils.GenericViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var adapter: FoodListAdapter? = null

//    haramm
//    private val dataSource: DataSourceFoodCatalog by lazy { DataSourceFoodList() }
//    private val dataSourceCatalog: DataSourceFoodCategory by lazy { DataSourceFoodCategoryImpl() }
   private val viewModel  : HomeViewModel by viewModels {
       val catalogDataSource = DummyCatalogDataSource()
    val catalogRepository: CatalogRepository = CatalogRepositoryImpl(catalogDataSource)
    val categoryDataSource = DummyCategoryDataSource()
    val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
    GenericViewModelFactory.create(HomeViewModel(categoryRepository,catalogRepository))
}
    private var isUsingGridMode: Boolean = false
    private val categoryAdapter = CategoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindFoodList(isUsingGridMode)
        setClickAction()
        bindCategoryFood()
    }

    private fun setClickAction() {
        binding.ivChangeMode.setOnClickListener {
            isUsingGridMode = !isUsingGridMode
            setIconMode(isUsingGridMode)
            bindFoodList(isUsingGridMode)
        }

    }

    private fun setIconMode(typeMode: Boolean) {
        binding.ivChangeMode.setImageResource(if (typeMode) R.drawable.ic_list_to_linier else R.drawable.ic_list_to_grid)
    }

    private fun bindFoodList(typeMode: Boolean) {
        val listMode = if (typeMode) FoodListAdapter.MODE_GRID else FoodListAdapter.MODE_LIST
        adapter = FoodListAdapter(
            listMode = listMode,
            listener = object : OnItemClickedListener<Catalog> {
                override fun onItemClicked(item: Catalog) {
                    navigateToDetail(item)
                }
            }
        )
        binding.rvListFood.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = GridLayoutManager(requireContext(), if (typeMode) 2 else 1)
        }
        adapter?.submitData(viewModel.getCatalogData())

    }

    private fun bindCategoryFood() {
        binding.rvListOfCategory.apply {
            adapter = this@HomeFragment.categoryAdapter
        }
        categoryAdapter.submitData(viewModel.getFoodListData())
    }


// using navigation
    /* private fun navigateToDetail(item: Catalog) {
         val navController = findNavController()
         val bundle = bundleOf(Pair(FoodDetailFragment.EXTRAS_ITEM, item))
         navController.navigate(R.id.action_foodCategoryListFragment_to_foodDetailFragment, bundle)
     }*/

    //    using intents for activity
    private fun navigateToDetail(item: Catalog) {
        DetailFoodActivity.startActivity(
            requireContext(), Catalog(
                item.category,
                item.name,
                item.description,
                item.price,
                item.location,
                item.image,
            )
        )
    }
}