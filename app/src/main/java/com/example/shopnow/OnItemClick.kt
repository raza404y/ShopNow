package com.example.shopnow

import com.example.shopnow.models.Products

interface OnItemClick {
    fun itemClick(products: Products,position: Int)
}