package com.example.detapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.PostDataModel
import com.example.detapp.repo.CreatePostRepo
import com.google.firebase.auth.FirebaseUser


class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CreatePostRepo
    private var userData: MutableLiveData<FirebaseUser?>
    private val loggedStatus: MutableLiveData<Boolean>

    init {
        repository = CreatePostRepo(application)
        userData = repository.firebaseUserMutableLiveData
        loggedStatus = repository.userLoggedMutableLiveData
    }

    fun createpost(postData: PostDataModel){
        Toast.makeText(getApplication(),"deneme",Toast.LENGTH_SHORT).show()
        repository.createPost(postData)
    }
    fun deneme(){
        repository.deneme()
    }
    fun firestore(){
        repository.denemeFirestore()
    }
    fun createPostFirestore(postData: PostDataModel){
        repository.createPostFirestore(postData)
    }
}

