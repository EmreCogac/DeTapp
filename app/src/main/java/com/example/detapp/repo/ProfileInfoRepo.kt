package com.example.detapp.repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.ProfileDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class ProfileInfoRepo(private val application: Application) {
    val firebaseUserMutableLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val userLoggedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    init {
        if (auth.currentUser != null) {
            firebaseUserMutableLiveData.postValue(auth.currentUser)
        }
    }

    fun profileInfo(profileDataModel: ProfileDataModel){
        val postRef = FirebaseDatabase.getInstance().getReference("posts")
        postRef.child(auth.currentUser!!.uid).get().addOnSuccessListener {

        }
    }
}