package com.fromryan.projectfoodapp.presentation.register

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
import com.fromryan.projectfoodapp.databinding.ActivityRegisterBinding
import com.fromryan.projectfoodapp.presentation.login.LoginActivity
import com.fromryan.projectfoodapp.presentation.main.MainActivity
import com.fromryan.projectfoodapp.utils.GenericViewModelFactory
import com.fromryan.projectfoodapp.utils.proceedWhen
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnRegister.setOnClickListener {
            doRegister()
        }
        binding.tvNavToLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun doRegister() {
        if (isFormValid()) {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val fullName = binding.editTextName.text.toString().trim()
            proceedRegister(email, password, fullName)
        }
    }

    private fun proceedRegister(email: String, password: String, fullName: String) {
        registerViewModel.doRegister(email, fullName, password).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoadingRegist.isVisible = false
                    binding.btnRegister.isVisible = true
                    navigateToMain()
                },
                doOnError = {
                    binding.pbLoadingRegist.isVisible = false
                    binding.btnRegister.isVisible = true
                    Toast.makeText(
                        this,
                        "Login Failed : ${it.exception?.message.orEmpty()}",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    binding.pbLoadingRegist.isVisible = true
                    binding.btnRegister.isVisible = false
                }
            )
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun isFormValid(): Boolean {
        val password = binding.editTextPassword.text.toString().trim()
        val confirmPassword = binding.editTextPwRepeat.text.toString().trim()
        val fullName = binding.editTextName.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()

        return checkNameValidation(fullName) && checkEmailValidation(email) &&
                checkPasswordValidation(password, binding.textFieldPasswordRegist) &&
                checkPasswordValidation(confirmPassword, binding.textFieldPwRepeat) &&
                checkPwdAndConfirmPwd(password, confirmPassword)

    }

    private fun checkNameValidation(fullName: String): Boolean {
        return if (fullName.isEmpty()) {
            binding.textFieldName.isErrorEnabled = true
            binding.textFieldName.error = getString(R.string.text_error_name_empty)
            false
        } else {
            binding.textFieldName.isErrorEnabled = false
            true
        }
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.textFieldEmailRegist.isErrorEnabled = true
            binding.textFieldEmailRegist.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textFieldEmailRegist.isErrorEnabled = true
            binding.textFieldEmailRegist.error = getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.textFieldEmailRegist.isErrorEnabled = false
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

    private fun checkPwdAndConfirmPwd(password: String, confirmPassword: String): Boolean {
        return if (password != confirmPassword) {
            binding.textFieldPasswordRegist.isErrorEnabled = true
            binding.textFieldPasswordRegist.error =
                getString(R.string.text_pw_nomatch)
            binding.textFieldPwRepeat.isErrorEnabled = true
            binding.textFieldPwRepeat.error =
                getString(R.string.text_pw_nomatch)
            false
        } else {
            binding.textFieldPasswordRegist.isErrorEnabled = false
            binding.textFieldPwRepeat.isErrorEnabled = false
            true
        }
    }
}