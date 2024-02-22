package com.example.detapp.repo

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.ProfileDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData

class ProfileInfoRepo(private val application: Application) {
    val firebaseUserMutableLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val userLoggedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val profileDataModel : ProfileDataModel? = null

    init {
        if (auth.currentUser != null) {
            firebaseUserMutableLiveData.postValue(auth.currentUser)
        }
    }

    fun profileInfo() : ProfileDataModel? {
        val postRef = FirebaseDatabase.getInstance().getReference("posts")
        postRef.child(auth.currentUser!!.uid).get().addOnSuccessListener {
                val email = it.child("email").value
                val name = it.child("name").value
                val surname = it.child("surname").value
                val username = it.child("username").value
                profileDataModel?.name = name.toString()
                profileDataModel?.email = email.toString()
                profileDataModel?.surname = surname.toString()
                profileDataModel?.username = username.toString()
                return@addOnSuccessListener
        }.addOnFailureListener{
            Toast.makeText(application, "sorun var ", Toast.LENGTH_SHORT).show()
            return@addOnFailureListener
        }
        return profileDataModel
    }

}