package com.example.shopnow.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopnow.Constants
import com.example.shopnow.adapters.WishListAdapter
import com.example.shopnow.databinding.FragmentCartBinding
import com.example.shopnow.models.WishList
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Cart : Fragment() {

    lateinit var binding : FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =  FragmentCartBinding.inflate(inflater, container, false)
    
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = WishListAdapter(requireContext())
        val arrayList = ArrayList<WishList>()
        binding.cartRV.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        binding.cartRV.layoutManager = layoutManager
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
    
        
        Firebase.firestore.collection(Constants.CART).document(Firebase.auth.currentUser!!.uid)
            .collection(Constants.ADDED_TO_CART).get().addOnSuccessListener { 
                if (!it.isEmpty){
                    arrayList.clear()
                    var totalBill = 0
                    for (i in it){
                        val products = i.toObject(WishList::class.java)
                        arrayList.add(products)
                        val quantity = products.quantityUser
                        val price = products.price
                        totalBill += calculateTotalItemPrice(quantity, price)
                    }
                    binding.totalRs.text = totalBill.toString()
                    adapter.submitList(arrayList)
                }else{
                    Toast.makeText(requireContext(), "Cart is Empty", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun calculateTotalItemPrice(quantity: Int, price: Int): Int {
        return quantity * price
    }
}