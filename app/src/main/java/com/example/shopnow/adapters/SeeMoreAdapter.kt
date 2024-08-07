package com.example.shopnow.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopnow.DiffUtilsCallback
import com.example.shopnow.R
import com.example.shopnow.activities.ProductDetails
import com.example.shopnow.databinding.SeeMoreRvDesignBinding
import com.example.shopnow.models.Products

class SeeMoreAdapter(val context: Context) :
    ListAdapter<Products, SeeMoreAdapter.SeeMoreViewHolder>(DiffUtilsCallback) {

    class SeeMoreViewHolder(var binding: SeeMoreRvDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(products: Products) {
            binding.apply {
                productName.text = products.productName
                productPrice.text = products.price.toString()
                Glide.with(itemView.context).load(products.productImgUrl)
                    .placeholder(R.drawable.emptyimg).into(productImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeMoreViewHolder {
        val binding =
            SeeMoreRvDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeeMoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeeMoreViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.setData(currentItem)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetails::class.java)
            intent.putExtra("id", currentItem.id)
            context.startActivity(intent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}

