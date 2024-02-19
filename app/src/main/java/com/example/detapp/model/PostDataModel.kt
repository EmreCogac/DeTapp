package com.example.detapp.model

import java.io.Serializable
import java.sql.Time


data class PostDataModel(
    var bookname: String,
    var time: String,
    var usermail: String

) : Serializable