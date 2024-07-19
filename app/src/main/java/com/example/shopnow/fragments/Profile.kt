package com.example.shopnow.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.shopnow.Constants
import com.example.shopnow.activities.EditProfile
import com.example.shopnow.activities.Login
import com.example.shopnow.databinding.FragmentProfileBinding
import com.example.shopnow.models.UserProfile
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class Profile : Fragment() {

   private lateinit var binding: com.example.shopnow.databinding.FragmentProfileBinding
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

        binding.editProfileBtn.setOnClickListener {
            startActivity(Intent(requireContext(),EditProfile::class.java))
        }
        binding.logoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(requireContext(),Login::class.java))
        }

        Firebase.database.reference.child(Constants.USERS_INFO).child(Firebase.auth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val userinfo = snapshot.getValue(UserProfile::class.java)

                        binding.profileImg.load(userinfo?.profileImg)

                        userinfo?.apply {
                            binding.nameEt.text = name
                            binding.emailEt.text = email
                            binding.phoneEt.text = phone
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

    }
}