package com.example.detapp.repo

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.PostDataModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.firestore


class CreatePostRepo(private val application: Application ){
    val firebaseUserMutableLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val userLoggedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    init {
        if (auth.currentUser != null) {
            firebaseUserMutableLiveData.postValue(auth.currentUser)
        }
    }

    fun deneme(){
        val database = FirebaseDatabase.getInstance().reference
        val yeniVeri = "Yeni Veri1234"
        val yeniVeriReferansi = database.child("veriler").push()
        yeniVeriReferansi.setValue(yeniVeri)
            .addOnCompleteListener {
                Toast.makeText(application,"oldu", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    fun denemeFirestore(){
        val db = Firebase.firestore
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815,
        )

        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }


    // create post with realtime database
//    fun createPost(postDataModel: PostDataModel){
//
//        val userid = auth.currentUser!!.uid + postDataModel.time+ postDataModel.bookname
//        val refPost = FirebaseDatabase.getInstance().getReference("posts").child(userid)
//        if (auth.currentUser != null) {
//            val postMap = HashMap<String, Any>()
//            postMap["usermail"] = postDataModel.usermail
//            postMap["bookname"] = postDataModel.bookname
//            postMap["time"] = postDataModel.time
//
//            refPost.setValue(postMap)
//                .addOnCompleteListener {task->
//                    if (!task.isSuccessful) {
//                        Toast.makeText(application,task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(application,"sorun mu var", Toast.LENGTH_SHORT).show()
//                    }
//
//                }
//        }else{
//            Toast.makeText(application,"problem",Toast.LENGTH_SHORT).show()
//        }
//
//    }

    @SuppressLint("SuspiciousIndentation")
    fun createPostFirestore(postDataModel: PostDataModel){
        val db = Firebase.firestore
        if (auth.currentUser != null) {
            val postMap = HashMap<String, Any>()
            postMap["usermail"] = postDataModel.usermail
            postMap["bookname"] = postDataModel.bookname
            postMap["time"] = postDataModel.time

                db.collection("Post").add(postMap)
                .addOnCompleteListener {task->
                    if (!task.isSuccessful) {
                        Toast.makeText(application,task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(application,"sorun mu var", Toast.LENGTH_SHORT).show()
                    }

                }
        }else{
            Toast.makeText(application,"problem",Toast.LENGTH_SHORT).show()
        }
    }
}