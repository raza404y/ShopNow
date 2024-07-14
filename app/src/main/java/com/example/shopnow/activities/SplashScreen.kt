package com.example.shopnow.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shopnow.databinding.ActivitySplashScreenBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SplashScreen : AppCompatActivity() {
    private val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.getStartBtn.setOnClickListener {
            val intent = Intent(this@SplashScreen, Login::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        if (Firebase.auth.currentUser!=null){
            startActivity(Intent(this@SplashScreen,MainActivity::class.java))
            finish()
        }
    }
}