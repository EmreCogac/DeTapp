package com.example.detapp.viewmodel

import android.app.Application
import android.provider.ContactsContract.Profile
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.ProfileDataModel
import com.example.detapp.repo.AuthRepo
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AuthRepo
    var userData: MutableLiveData<FirebaseUser?>
    private val loggedStatus: MutableLiveData<Boolean>

    init {
        repository = AuthRepo(application)
        userData = repository.firebaseUserMutableLiveData
        loggedStatus = repository.userLoggedMutableLiveData
    }

    fun register(userData: ProfileDataModel) {
        repository.register(userData, Application())
    }


    fun deneme(){
        repository.deneme()
    }
    fun login(email: String?, pass: String?) {
        repository.login(email, pass)
    }

    fun signOut() {
        repository.signOut()
    }
}

