package com.example.shopnow.models

class UserProfile {
    var name: String? = null
    var email: String? = null
    var phone: String? = null
    var profileImg: String? = null
    constructor()
    constructor(name: String?, email: String?, phone: String?,profileimg: String?) {
        this.name = name
        this.email = email
        this.phone = phone
        this.profileImg = profileimg
    }


}