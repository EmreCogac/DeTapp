package com.example.detapp.repo

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.PostDataModel
import com.example.detapp.viewmodel.PostViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlin.concurrent.timerTask

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


    fun createPost(postDataModel: PostDataModel){
        val userid = auth.currentUser!!.uid + postDataModel.time+ postDataModel.bookname
        val refPost = FirebaseDatabase.getInstance().getReference("posts").child(userid)
        if (auth.currentUser != null) {
            val postMap = HashMap<String, Any>()
            postMap["usermail"] = postDataModel.usermail
            postMap["bookname"] = postDataModel.bookname
            postMap["time"] = postDataModel.time

            refPost.setValue(postMap)
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