package com.example.shopnow.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.shopnow.R
import com.example.shopnow.databinding.ActivityMainBinding
import com.example.shopnow.fragments.Cart
import com.example.shopnow.fragments.Favourite
import com.example.shopnow.fragments.Home
import com.example.shopnow.fragments.Profile
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        loadFragment(Home())

        binding.bottomNav.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home -> {
                    loadFragment(Home())
                }
                R.id.fvrt -> {
                    loadFragment(Favourite())
                }
                R.id.cart -> {
                    loadFragment(Cart())
                }
                R.id.profile -> {
                    loadFragment(Profile())
                }
            }
             true
        }

    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}