package com.example.shopnow

import androidx.recyclerview.widget.DiffUtil
import com.example.shopnow.models.WishList

object wishlistDiff : DiffUtil.ItemCallback<WishList>() {
    override fun areItemsTheSame(oldItem: WishList, newItem: WishList): Boolean {
        return oldItem.productName == newItem.productName
    }

    override fun areContentsTheSame(oldItem: WishList, newItem: WishList): Boolean {
        return oldItem.price == newItem.price &&
                oldItem.productImgUrl == newItem.productImgUrl &&
                oldItem.quantityUser == newItem.quantityUser
    }
}