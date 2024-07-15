package com.example.shopnow

import androidx.recyclerview.widget.DiffUtil
import com.example.shopnow.models.Products

object DiffUtilsCallback : DiffUtil.ItemCallback<Products>() {
    override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem.productName == newItem.productName
    }

    override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
        return oldItem.price == newItem.price &&
                oldItem.productImgUrl == newItem.productImgUrl
    }
}