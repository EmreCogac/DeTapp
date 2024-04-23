package com.example.detapp.viewmodel

import android.app.Application
import android.view.View

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.detapp.adapter.PostAdapter
import com.example.detapp.model.PostReadModel
import com.example.detapp.model.ProfileDataModel
import com.example.detapp.model.ProfileInfoDataModel

import com.example.detapp.repo.ProfileInfoRepo
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.database.snapshots
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import java.util.concurrent.Flow

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProfileInfoRepo
    private var userData: MutableLiveData<FirebaseUser?>
    private val loggedStatus: MutableLiveData<Boolean>


    init {
        repository = ProfileInfoRepo(application)
        userData = repository.firebaseUserMutableLiveData
        loggedStatus = repository.userLoggedMutableLiveData

    }

    fun deneme() : LiveData<ProfileInfoDataModel> {
        return repository.profileInfo()
    }

    fun profileDeneme() : kotlinx.coroutines.flow.Flow<ProfileInfoDataModel> {

        return repository.profileDeneme()

    }



}