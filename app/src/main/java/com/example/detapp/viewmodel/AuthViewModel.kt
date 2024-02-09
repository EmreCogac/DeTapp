package com.codingstuff.loginsignupmvvm.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.detapp.repo.AuthRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AuthRepo
    val userData: MutableLiveData<FirebaseUser?>
    val loggedStatus: MutableLiveData<Boolean>

    init {
        repository = AuthRepo(application)
        userData = repository.firebaseUserMutableLiveData
        loggedStatus = repository.userLoggedMutableLiveData
    }

    fun register(email: String?, pass: String?) {
        repository.register(email, pass)
    }

    fun login(email: String?, pass: String?) {
        repository.login(email, pass)
    }

    fun signOut() {
        repository.signOut()
    }
}

