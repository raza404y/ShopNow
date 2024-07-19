package com.example.shopnow.models

class WishList {
    var productImgUrl: String = ""
    var productName: String = ""
    var price: Int = 0
    var quantityUser: Int = 1

    constructor()
    constructor(
        productImgUrl: String,
        productName: String,
        price: Int,
        quantityUser: Int
    ) {
        this.productImgUrl = productImgUrl
        this.productName = productName
        this.price = price
        this.quantityUser = quantityUser
    }

}