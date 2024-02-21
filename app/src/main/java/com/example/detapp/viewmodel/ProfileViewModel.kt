package com.example.detapp.viewmodel

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.ProfileDataModel

import com.example.detapp.repo.ProfileInfoRepo
import com.google.firebase.auth.FirebaseUser

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProfileInfoRepo
    private var userData: MutableLiveData<FirebaseUser?>
    private val loggedStatus: MutableLiveData<Boolean>

    init {
        repository = ProfileInfoRepo(application)
        userData = repository.firebaseUserMutableLiveData
        loggedStatus = repository.userLoggedMutableLiveData
    }

    fun profileInfo(model: ProfileDataModel){
        repository.profileInfo(model)
    }

}