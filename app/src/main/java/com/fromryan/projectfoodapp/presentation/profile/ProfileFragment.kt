package com.fromryan.projectfoodapp.presentation.profile


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.databinding.FragmentProfileBinding
import com.fromryan.projectfoodapp.presentation.main.SharedPreferenceMainManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
        getProfileData()
        changeEditMode()
        setSwitchMode()
    }


    private fun getProfileData() {
        viewModel.profileData.observe(viewLifecycleOwner) {
            binding.ivProfile.load(it.profileImg) {
                crossfade(true)
                error(R.drawable.ic_launcher_background)
                transformations(CircleCropTransformation())
            }
            binding.etNameTextProfile.setText(it.name)
            binding.etNomorTextProfile.setText(it.nohp)
            binding.etEmailTextProfile.setText(it.email)
        }
    }
    private fun setSwitchMode() {
        val themeTitleList = arrayOf("Light", "Dark", "Auto (System Default)")
        val sharedPreferencesManager = SharedPreferenceMainManager(requireContext())
        var checkedTheme = sharedPreferencesManager.theme
        val  themeDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Theme")
            .setPositiveButton("ok"){_,_ ->
                sharedPreferencesManager.theme = checkedTheme
                AppCompatDelegate.setDefaultNightMode(sharedPreferencesManager.themeFlag[checkedTheme])
            }
            .setSingleChoiceItems(themeTitleList,checkedTheme){_, which ->
                checkedTheme = which
            }
            .setCancelable(false)
        binding.switchMode.setOnClickListener {
            themeDialog.show()
        }
    }

    private fun setClickListener() {
        binding.btnEditProfile.setOnClickListener {
            count += 1
            viewModel.changeEditMode()
            if (count %2 == 0){
                binding.btnEditProfile.setText(getString(R.string.text_edit_profile))
            }else{
                binding.btnEditProfile.setText(getString(R.string.text_save))
            }
        }

    }
    private fun changeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.etNameTextProfile.isEnabled = it
            binding.etEmailTextProfile.isEnabled = it
            binding.etNomorTextProfile.isEnabled = it
        }
    }

}