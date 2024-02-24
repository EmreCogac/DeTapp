package com.example.detapp.viewmodel

import android.app.Application
import android.provider.ContactsContract.Profile
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.ProfileDataModel
import com.example.detapp.model.ProfileInfoDataModel
import com.example.detapp.repo.AuthRepo
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.Executor
import java.util.concurrent.Flow


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


    fun flowDeneme() : kotlinx.coroutines.flow.Flow<kotlinx.coroutines.flow.Flow<ProfileInfoDataModel>> = flow {
       try {
           emit(repository.getProfileInfo())
       }catch (e:Exception){
           return@flow
       }
    }.flowOn(Dispatchers.Default)
    fun login(email: String?, pass: String?) {
        repository.login(email, pass)
    }

    fun signOut() {
        repository.signOut()
    }
}

