package com.example.detapp.model

import java.io.Serializable

data class PostReadModel(
    var bookname: String= " ",
    var time : String =  "",
    var uid :String =" ",
    var usermail: String = " ",
    var postid: String = " ",
) : Serializable
