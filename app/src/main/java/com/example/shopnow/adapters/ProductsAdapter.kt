package com.example.shopnow.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopnow.R
import com.example.shopnow.databinding.ForsaleDesignRvBinding
import com.example.shopnow.models.Products

class ProductsAdapter(private val list: ArrayList<Products>, private val context: Context) :
    RecyclerView.Adapter<ProductsAdapter.mViewHolder>() {

    class mViewHolder(val binding: ForsaleDesignRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        val view = ForsaleDesignRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return mViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {

        holder.binding.productName.text = list[position].productName
        holder.binding.productPrice.text = list[position].price.toString()
        val url = list[position].productImgUrl
        if (url.isNotEmpty()) {
            Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.dresss)
                .into(holder.binding.productImage)
        }
    }

}