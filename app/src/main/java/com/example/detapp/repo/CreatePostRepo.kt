package com.example.detapp.repo

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.PostDataModel
import com.example.detapp.viewmodel.PostViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class CreatePostRepo(private val application: Application ){
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun createPost(postDataModel: PostDataModel, application: Application){
        val userid = auth.currentUser!!.uid
        val refPost = FirebaseDatabase.getInstance().getReference("posts").child(userid)

        if (auth.currentUser != null) {
            val postMap = HashMap<String, Any>()
            postMap["bookname"] = postDataModel.bookname
            postMap["user"] = userid
            postMap["preuser"] = postDataModel.preuser!!
            postMap["time"] = postDataModel.time
            refPost.setValue(postMap)
                .addOnCompleteListener {task->
                    if (!task.isSuccessful) {
                        Toast.makeText(application,task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}