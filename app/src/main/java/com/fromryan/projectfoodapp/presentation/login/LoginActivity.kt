package com.fromryan.projectfoodapp.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.fromryan.projectfoodapp.R
import com.fromryan.projectfoodapp.data.datasource.auth.AuthDataSource
import com.fromryan.projectfoodapp.data.datasource.auth.FirebaseAuthDataSource
import com.fromryan.projectfoodapp.data.repository.UserRepository
import com.fromryan.projectfoodapp.data.repository.UserRepositoryImpl
import com.fromryan.projectfoodapp.data.source.firebase.FirebaseService
import com.fromryan.projectfoodapp.data.source.firebase.FirebaseServiceImpl
import com.fromryan.projectfoodapp.databinding.ActivityLoginBinding
import com.fromryan.projectfoodapp.presentation.main.MainActivity
import com.fromryan.projectfoodapp.presentation.register.RegisterActivity
import com.fromryan.projectfoodapp.utils.GenericViewModelFactory
import com.fromryan.projectfoodapp.utils.proceedWhen
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
        private val binding: ActivityLoginBinding by lazy {
            ActivityLoginBinding.inflate(layoutInflater)
        }
        private val loginViewModel: LoginViewModel by viewModel()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(binding.root)
            setClickListener()
            observeResult()
        }

        private fun setClickListener() {
            binding.ivButtonBack.setOnClickListener{
                finish()
            }
            binding.btnLogin.setOnClickListener {
                doLogin()
            }
            binding.tvNavToRegister.setOnClickListener {
                navigateToRegister()
            }
        }

        private fun navigateToMain() {
            startActivity(Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }

        private fun navigateToRegister() {
            startActivity(Intent(this, RegisterActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
        }

        private fun observeResult() {
            loginViewModel.loginResult.observe(this) {
                it.proceedWhen(
                    doOnSuccess = {
                        binding.pbLoadingLogin.isVisible = false
                        binding.btnLogin.isVisible = true
                        navigateToMain()
                    },
                    doOnError = {
                        binding.pbLoadingLogin.isVisible = false
                        binding.btnLogin.isVisible = true
                        Toast.makeText(
                            this,
                            "Login Failed : ${it.exception?.message.orEmpty()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    doOnLoading = {
                        binding.pbLoadingLogin.isVisible = true
                        binding.btnLogin.isVisible = false
                    }
                )
            }
        }

        private fun doLogin() {
            if (isFormValid()) {
                val email = binding.editTeksLoginEmail.text.toString().trim()
                val password = binding.editTextLoginPassword.text.toString().trim()
                loginViewModel.doLogin(email, password)
            }
        }

        private fun isFormValid(): Boolean {
            val email = binding.editTeksLoginEmail.text.toString().trim()
            val password = binding.editTextLoginPassword.text.toString().trim()

            return checkEmailValidation(email) &&
                    checkPasswordValidation(password, binding.textFieldPassword)
        }

        private fun checkEmailValidation(email: String): Boolean {
            return if (email.isEmpty()) {
                binding.textFieldEmail.isErrorEnabled = true
                binding.textFieldEmail.error = getString(R.string.text_error_email_empty)
                false
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.textFieldEmail.isErrorEnabled = true
                binding.textFieldEmail.error = getString(R.string.text_error_email_invalid)
                false
            } else {
                binding.textFieldEmail.isErrorEnabled = false
                true
            }
        }

        private fun checkPasswordValidation(
            confirmPassword: String,
            textInputLayout: TextInputLayout
        ): Boolean {
            return if (confirmPassword.isEmpty()) {
                textInputLayout.isErrorEnabled = true
                textInputLayout.error =
                    getString(R.string.text_error_pw_empty)
                false
            } else if (confirmPassword.length < 8) {
                textInputLayout.isErrorEnabled = true
                textInputLayout.error =
                    getString(R.string.text_error_pw_lower)
                false
            } else {
                textInputLayout.isErrorEnabled = false
                true
            }
        }
    }