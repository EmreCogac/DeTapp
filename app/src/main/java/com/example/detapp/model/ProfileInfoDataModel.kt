package com.example.detapp.model

import java.io.Serializable

data class ProfileInfoDataModel(
    var name: String = "",
    var surname: String = "",
    var username: String = "",
    var email: String = ""
) {
    constructor() : this("", "", "", "")
}

