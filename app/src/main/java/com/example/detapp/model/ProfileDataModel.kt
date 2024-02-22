package com.example.detapp.model

import java.io.Serializable
import java.util.UUID

data class ProfileDataModel(
    var name: String,
    var surname: String,
    var username: String,
    var email: String,
    var pass:String,
) : Serializable {

}
