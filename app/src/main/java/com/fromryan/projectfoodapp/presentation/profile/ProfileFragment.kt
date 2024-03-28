package com.fromryan.projectfoodapp.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

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
        setClickListener()
        changeEditMode()
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

    private fun setClickListener() {
        binding.btnEditProfile.setOnClickListener {
            viewModel.changeEditMode()
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