package com.example.springboot_project_front.ui.products

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.springboot_project_front.R
import com.example.springboot_project_front.databinding.ActivityMainBinding
import com.example.springboot_project_front.ui.auth.AuthActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val token = this.intent.getStringExtra("token")
        Log.d("TAG", "onCreate: $token")
        Toast.makeText(this, token, Toast.LENGTH_LONG).show()

        val bundle = Bundle().apply { putString("token", token) }

        bindViews()

//        supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.fragment_container, SportingGoodsFragment::class.java, bundle)
//            .commit()
    }

    private fun bindViews() {
        binding.btnLogout.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
    }
}