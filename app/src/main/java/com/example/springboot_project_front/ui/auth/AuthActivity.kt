package com.example.springboot_project_front.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.springboot_project_front.R
import com.example.springboot_project_front.base.hide
import com.example.springboot_project_front.base.show
import com.example.springboot_project_front.databinding.ActivityAuthBinding
import com.example.springboot_project_front.ui.products.MainActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindViews()
        setupObservers()
    }

    private fun bindViews() {
        binding.tvNoAccount.setOnClickListener {
            binding.btnLogin.text = getString(R.string.register)
        }
        binding.btnLogin.setOnClickListener {
            if (binding.btnLogin.text == getString(R.string.register)) {
                viewModel.register(
                    email = binding.etEmail.text.toString(),
                    password = binding.etPassword.text.toString()
                )
            } else {
                viewModel.login(
                    email = binding.etEmail.text.toString(),
                    password = binding.etPassword.text.toString()
                )
            }
        }
    }

    private fun setupObservers() {
        viewModel.state.onEach { state ->
            when (state) {
                is AuthViewModel.State.Error -> {
                    Log.d("TAG", "setupObservers: login error")
                }
                AuthViewModel.State.HideLoading -> {
                    binding.btnLogin.show()
                    binding.pbLoading.hide()
                }
                AuthViewModel.State.ShowLoading -> {
                    binding.btnLogin.hide()
                    binding.pbLoading.show()
                }
                is AuthViewModel.State.Success -> {
                    val token = state.token
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("token", token)
                    }
                    startActivity(intent)
                }
            }
        }.launchIn(lifecycleScope)
    }
}