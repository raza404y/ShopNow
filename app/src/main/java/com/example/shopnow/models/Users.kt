package com.example.shopnow.models

class Users {

    var firstName = ""
    var lastName = ""
    var email = ""
    var password = ""

    constructor(firstName: String, lastName: String, email: String, password: String){
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.password = password
    }

    constructor()

}