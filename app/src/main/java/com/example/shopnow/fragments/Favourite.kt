package com.example.shopnow.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopnow.Constants
import com.example.shopnow.R
import com.example.shopnow.adapters.SeeMoreAdapter
import com.example.shopnow.adapters.WishListAdapter
import com.example.shopnow.databinding.FragmentFavouriteBinding
import com.example.shopnow.models.Products
import com.example.shopnow.models.WishList
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class Favourite : Fragment() {
    private lateinit var binding: FragmentFavouriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = WishListAdapter(requireContext())
        val arrayList = ArrayList<WishList>()
        binding.favouritesRV.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.favouritesRV.layoutManager = layoutManager
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true

        Firebase.firestore.collection(Constants.WISHLIST).document(Firebase.auth.currentUser!!.uid)
            .collection(Constants.FAVOURITE).get().addOnSuccessListener {
                if (!it.isEmpty){
                    arrayList.clear()
                    for (p in it){
                        val products = p.toObject(WishList::class.java)
                        arrayList.add(products)
                    }
                    adapter.submitList(arrayList)
                }else{
                    Toast.makeText(requireContext(), "empty list", Toast.LENGTH_SHORT).show()
                }
            }

    }
}