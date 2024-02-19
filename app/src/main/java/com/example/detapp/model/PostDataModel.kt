package com.example.detapp.model

import java.io.Serializable
import java.sql.Time


abstract class PostDataModel(
    var bookname: String,
    var preuser: String? = null,
    var time: String
) : Serializable