package com.example.detapp.repo

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.ProfileDataModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AuthRepo(private val application: Application) {

    val firebaseUserMutableLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val userLoggedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        if (auth.currentUser != null) {
            firebaseUserMutableLiveData.postValue(auth.currentUser)
        }
    }

    fun register(profileDataModel: ProfileDataModel, application: Application) {
        auth.createUserWithEmailAndPassword(profileDataModel.email, profileDataModel.pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                            val userid = auth.currentUser!!.uid
                            val refProfile = FirebaseDatabase.getInstance().getReference("users").child(userid)

                            val userMap = HashMap<String, Any>()
                            userMap["userId"] = userid
                            userMap["username"] = profileDataModel.username
                            userMap["name"] = profileDataModel.name
                            userMap["surname"] = profileDataModel.surname
                            userMap["email"] = profileDataModel.email

                            refProfile.setValue(userMap)
                                .addOnCompleteListener { task ->
                                    if (!task.isSuccessful) {
                                        Toast.makeText(application,task.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                                    }
                                }
                    firebaseUserMutableLiveData.postValue(auth.currentUser)
                }
            }
    }

    fun deneme(){
        val database = FirebaseDatabase.getInstance().reference
        val yeniVeri = "Yeni Veri1"
        val yeniVeriReferansi = database.child("verile1").push()
        yeniVeriReferansi.setValue(yeniVeri)
            .addOnCompleteListener {
                Toast.makeText(application,"oldu", Toast.LENGTH_SHORT)
                    .show()
            }

    }

    fun login(email: String?, pass: String?) {
        auth.signInWithEmailAndPassword(email!!, pass!!).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseUserMutableLiveData.postValue(auth.currentUser)
            } else {
                val errorMessage = task.exception?.message
                Toast.makeText(application, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun signOut() {
        auth.signOut()
        userLoggedMutableLiveData.postValue(true)
    }
}