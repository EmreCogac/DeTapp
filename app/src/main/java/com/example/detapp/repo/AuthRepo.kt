package com.example.detapp.repo

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepo(private val application: Application) {
    var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
        get() {
            return firebaseUserMutableLiveData
        }

    var userLogMutableLiveData: MutableLiveData<Boolean?> = MutableLiveData()
        get() {
            return userLogMutableLiveData
        }

    var auth: FirebaseAuth = FirebaseAuth.getInstance()


    init {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            firebaseUserMutableLiveData.postValue(currentUser)
        }
    }

    fun register(email: String, pass : String){
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUserMutableLiveData.postValue(auth.currentUser)
                } else {
                    val exception = task.exception
                    Toast.makeText(application, exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun login(email: String, pass: String){
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
            if(task.isSuccessful){
                firebaseUserMutableLiveData.postValue(auth.currentUser)
            } else {
                val exception = task.exception
                Toast.makeText(application, exception?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun signout(){
        auth.signOut()
        userLogMutableLiveData.postValue(true)
    }

}