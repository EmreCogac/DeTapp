package com.example.detapp.repo

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.ProfileDataModel
import com.example.detapp.model.ProfileInfoDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.snapshots
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import java.util.Objects

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


    private val ref = FirebaseDatabase.getInstance().getReference("users")


    fun profileDeneme () : Flow<ProfileInfoDataModel>{
        Thread.sleep(1_000)
        return ref.child(auth.currentUser!!.uid).snapshots.mapNotNull {
            it.getValue<ProfileInfoDataModel>()
        }.flowOn(Dispatchers.IO)
    }

    fun deneme(): LiveData<ProfileInfoDataModel> {
        val profileLiveData = MutableLiveData<ProfileInfoDataModel>()
        val postRef = FirebaseDatabase.getInstance().getReference("users")

        postRef.child(auth.currentUser!!.uid).get().addOnSuccessListener { dataSnapshot ->
            val name = dataSnapshot.child("name").value.toString()
            val email = dataSnapshot.child("email").value.toString()
            val surname = dataSnapshot.child("surname").value.toString()
            val username = dataSnapshot.child("username").value.toString()
            val profileDataModel = ProfileInfoDataModel(name, surname, username, email)
            profileLiveData.value = profileDataModel
        }.addOnFailureListener {
            Toast.makeText(application, "sorun var ", Toast.LENGTH_SHORT).show()
        }

        return profileLiveData
    }
}