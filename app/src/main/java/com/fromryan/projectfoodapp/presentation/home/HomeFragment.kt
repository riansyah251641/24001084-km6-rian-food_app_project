package com.fromryan.projectfoodapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.model.Catalog
import com.fromryan.projectfoodapp.data.model.Category
import com.fromryan.projectfoodapp.databinding.FragmentHomeBinding
import com.fromryan.projectfoodapp.presentation.detailfood.DetailFoodActivity
import com.fromryan.projectfoodapp.presentation.home.adapter.CatalogAdapter
import com.fromryan.projectfoodapp.presentation.home.adapter.CategoryAdapter
import com.fromryan.projectfoodapp.presentation.home.adapter.OnItemClickedListener
import com.fromryan.projectfoodapp.presentation.main.MainActivity
import com.fromryan.projectfoodapp.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var catalogAdapter: CatalogAdapter? = null

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
            getCatalogData(it.name)
        }
    }

    private val homeViewModel: HomeViewModel by viewModel()
    private var isUsingGridMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
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

        binding.ivSearchIcon.setOnClickListener {
            navigationToProfile()
        }
    }

    private fun navigationToProfile() {
        if (requireActivity() !is MainActivity) return
        (requireActivity() as MainActivity).navigateToProfile()
    }

    private fun setIconMode(typeMode: Boolean) {
        binding.ivChangeMode.setImageResource(if (typeMode) R.drawable.ic_list_to_linier else R.drawable.ic_list_to_grid)
    }

    private fun bindFoodList(typeMode: Boolean) {
        val listMode = if (typeMode) CatalogAdapter.MODE_GRID else CatalogAdapter.MODE_LIST
        catalogAdapter =
            CatalogAdapter(
                listMode = listMode,
                listener =
                    object : OnItemClickedListener<Catalog> {
                        override fun onItemClicked(item: Catalog) {
                            navigateToDetail(item)
                        }

                        override fun onItemAddedToCart(item: Catalog) {
                            homeViewModel.addItemToCart(item)
                        }
                    },
            )
        binding.rvListFood.apply {
            adapter = this@HomeFragment.catalogAdapter
            layoutManager = GridLayoutManager(requireContext(), if (typeMode) 2 else 1)
        }
        getCatalogData(null)
    }

    private fun bindCategoryFood() {
        binding.rvListOfCategory.apply {
            adapter = this@HomeFragment.categoryAdapter
        }
        getCategoryData()
    }

// using navigation
    /* private fun navigateToDetail(item: Catalog) {
         val navController = findNavController()
         val bundle = bundleOf(Pair(FoodDetailFragment.EXTRAS_ITEM, item))
         navController.navigate(R.id.action_foodCategoryListFragment_to_foodDetailFragment, bundle)
     }*/

    // submit Catalog Data
    private fun getCatalogData(categoryName: String? = null) {
        homeViewModel.getCatalogData(categoryName).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoadingEmptyStateCategoryLottie.isVisible = false
                    binding.rvListFood.isVisible = true
                    it.payload?.let { data -> bindCatalog(data) }
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoadingEmptyStateCategoryLottie.isVisible = true
                    binding.rvListFood.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoadingEmptyStateCategoryLottie.isVisible = false
                    binding.rvListFood.isVisible = false
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoadingEmptyStateCategoryLottie.isVisible = true
                    binding.rvListFood.isVisible = false
                },
            )
        }
    }

//    submit categories data

    private fun getCategoryData() {
        homeViewModel.getCategoryData().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutStateCategory.root.isVisible = false
                    binding.layoutStateCategory.pbLoadingEmptyState.isVisible = false
                    binding.rvListOfCategory.isVisible = true
                    it.payload?.let { data -> bindCategory(data) }
                },
                doOnError = {
                    binding.rvListOfCategory.isVisible = false
                },
                doOnEmpty = {
                    binding.rvListOfCategory.isVisible = false
                },
                doOnLoading = {
                    binding.layoutStateCategory.root.isVisible = true
                    binding.layoutStateCategory.pbLoadingEmptyState.isVisible = true
                    binding.rvListOfCategory.isVisible = false
                },
            )
        }
    }

    // bind catalog
    private fun bindCatalog(catalog: List<Catalog>) {
        catalogAdapter?.submitData(catalog)
    }

    //    bind category
    private fun bindCategory(categories: List<Category>) {
        categoryAdapter.submitData(categories)
    }

    //    using intents for activity
    private fun navigateToDetail(item: Catalog) {
        DetailFoodActivity.startActivity(
            requireContext(),
            Catalog(
                item.id,
                item.name,
                item.description,
                item.price,
                item.location,
                item.image,
            ),
        )
    }
}
