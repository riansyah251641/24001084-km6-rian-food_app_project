package com.fromryan.projectfoodapp.presentation.profile


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.datasource.auth.AuthDataSource
import com.fromryan.projectfoodapp.data.datasource.auth.FirebaseAuthDataSource
import com.fromryan.projectfoodapp.data.repository.UserRepository
import com.fromryan.projectfoodapp.data.repository.UserRepositoryImpl
import com.fromryan.projectfoodapp.data.source.firebase.FirebaseService
import com.fromryan.projectfoodapp.data.source.firebase.FirebaseServiceImpl
import com.fromryan.projectfoodapp.databinding.FragmentProfileBinding
import com.fromryan.projectfoodapp.presentation.login.LoginActivity
import com.fromryan.projectfoodapp.presentation.main.MainActivity
import com.fromryan.projectfoodapp.presentation.main.SharedPreferenceMainManager
import com.fromryan.projectfoodapp.utils.GenericViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels {
        val user: FirebaseService = FirebaseServiceImpl()
        val userDataSource: AuthDataSource = FirebaseAuthDataSource(user)
        val userRepo: UserRepository = UserRepositoryImpl(userDataSource)
        GenericViewModelFactory.create(ProfileViewModel(userRepo))
    }

    var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkIfUserLogin()
        setClickListener()
        getProfileData()
        changeEditMode()
        setSwitchMode()
    }


    private fun getProfileData() {
        viewModel.getCurrentUser()?.let {
            binding.etNameTextProfile.setText(it.fullName)
            binding.etEmailTextProfile.setText(it.email)
        }
    }

    private fun checkIfUserLogin() {
        if (viewModel.isUserLoggedIn()) {

        } else {
            navigateToLogin()
        }
    }

    private fun setSwitchMode() {
        val themeTitleList = arrayOf("Light", "Dark", "Auto (System Default)")
        val sharedPreferencesManager = SharedPreferenceMainManager(requireContext())
        var checkedTheme = sharedPreferencesManager.theme
        val themeDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Theme")
            .setPositiveButton("ok") { _, _ ->
                sharedPreferencesManager.theme = checkedTheme
                AppCompatDelegate.setDefaultNightMode(sharedPreferencesManager.themeFlag[checkedTheme])
            }
            .setSingleChoiceItems(themeTitleList, checkedTheme) { _, which ->
                checkedTheme = which
            }
            .setCancelable(false)

        binding.switchMode.setOnClickListener {
            themeDialog.show()
        }
    }

    private fun setClickListener() {
            binding.btnEditProfile.setOnClickListener {
                if (viewModel.isUserLoggedIn()) {
                count += 1
                viewModel.changeEditMode()
                if (count % 2 == 0) {
                    binding.btnEditProfile.setText(getString(R.string.text_edit_profile))
                } else {
                    binding.btnEditProfile.setText(getString(R.string.text_save))
                }


                } else {
                    navigateToLogin()
                }
            }

            binding.logoutProfile.setOnClickListener {
                if (viewModel.isUserLoggedIn()) {
                logoutUser()
                } else {
                    navigateToLogin()
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

    private fun logoutUser() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_dialog_logout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel_dialog)
        val btnLogout = dialog.findViewById<Button>(R.id.btn_logout_dialog)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnLogout.setOnClickListener {
            dialog.dismiss()
            viewModel.doLogout()
            navigateToHome()
        }
        dialog.show()
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
    }

    private fun navigateToHome() {
        startActivity(Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }


}