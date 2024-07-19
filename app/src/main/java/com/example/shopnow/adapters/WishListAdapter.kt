package com.example.shopnow.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import com.bumptech.glide.Glide
import com.example.shopnow.R
import com.example.shopnow.databinding.WishslistRvDesignBinding
import com.example.shopnow.models.WishList
import com.example.shopnow.wishlistDiff

class WishListAdapter(private val context: Context):ListAdapter<WishList,WishListAdapter.viewholder>(wishlistDiff) {

    class viewholder(var binding: WishslistRvDesignBinding):RecyclerView.ViewHolder(binding.root){
        fun setData(wishList: WishList){
            Glide.with(itemView.context)
                .load(wishList.productImgUrl)
                .placeholder(R.drawable.emptyimg)
                .into(binding.productImage)
            binding.productName.text = wishList.productName
            binding.productPrice.text = wishList.price.toString()
            binding.quantity.text = "Quantity : ${wishList.quantityUser}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val binding = WishslistRvDesignBinding.inflate(LayoutInflater.from(context),parent,false)
        return viewholder(binding)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val currentItem = getItem(position)
        holder.setData(currentItem)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
