package com.example.detapp.repo

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.ProfileDataModel
import com.example.detapp.model.ProfileInfoDataModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class AuthRepo(private val application: Application) {

    val firebaseUserMutableLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val userLoggedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        if (auth.currentUser != null) {
            firebaseUserMutableLiveData.postValue(auth.currentUser)
        }
    }

    fun register(profileDataModel: ProfileDataModel, application: Application){
        val db = Firebase.firestore
        auth.createUserWithEmailAndPassword(profileDataModel.email, profileDataModel.pass)
            .addOnCompleteListener { task ->
                if ( task.isSuccessful) {
                    val userid = auth.currentUser!!.uid
                    val userMap = HashMap<String, Any>()
                    userMap["userId"] = userid
                    userMap["username"] = profileDataModel.username
                    userMap["name"] = profileDataModel.name
                    userMap["surname"] = profileDataModel.surname
                    userMap["email"] = profileDataModel.email

                    db.collection("Users").add(userMap)
                        .addOnCompleteListener { task ->
                            if (!task.isSuccessful){
                                Toast.makeText(application,task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    firebaseUserMutableLiveData.postValue(auth.currentUser)
                }

            }
    }
    // register realtime database
//    fun register(profileDataModel: ProfileDataModel, application: Application) {
//        auth.createUserWithEmailAndPassword(profileDataModel.email, profileDataModel.pass)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                            val userid = auth.currentUser!!.uid
//                            val refProfile = FirebaseDatabase.getInstance().getReference("users").child(userid)
//                            val userMap = HashMap<String, Any>()
//                            userMap["userId"] = userid
//                            userMap["username"] = profileDataModel.username
//                            userMap["name"] = profileDataModel.name
//                            userMap["surname"] = profileDataModel.surname
//                            userMap["email"] = profileDataModel.email
//
//                            refProfile.setValue(userMap)
//                                .addOnCompleteListener { task ->
//                                    if (!task.isSuccessful) {
//                                        Toast.makeText(application,task.exception?.message.toString(),Toast.LENGTH_SHORT).show()
//                                    }
//                                }
//                    firebaseUserMutableLiveData.postValue(auth.currentUser)
//                }
//            }
//    }

    suspend fun getProfileInfo(): Flow<ProfileInfoDataModel> = flow {
        val postRef = FirebaseDatabase.getInstance().getReference("users")
        try {
            val dataSnapshot = postRef.child(auth.currentUser!!.uid).get().await() // await hele bi dur diyor bu yüzden asennkron
            val name = dataSnapshot.child("name").value.toString()
            val email = dataSnapshot.child("email").value.toString()
            val surname = dataSnapshot.child("surname").value.toString()
            val username = dataSnapshot.child("username").value.toString()
            val profileDataModel = ProfileInfoDataModel(name, surname, username, email)
            emit(profileDataModel)
        } catch (e: Exception) {
           return@flow
        }
    }.flowOn(Dispatchers.Default)




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