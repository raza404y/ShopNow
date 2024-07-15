package com.example.shopnow.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shopnow.R
import com.example.shopnow.activities.Login
import com.example.shopnow.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Profile : Fragment() {

   private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(requireContext(),Login::class.java))
        }
    }
}