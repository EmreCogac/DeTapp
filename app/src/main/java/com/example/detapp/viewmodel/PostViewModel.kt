package com.example.detapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.detapp.model.PostDataModel
import com.example.detapp.repo.CreatePostRepo


class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CreatePostRepo


    init {
        repository = CreatePostRepo(application)
    }

    fun createpost(postData: PostDataModel){
        repository.createPost(postData, Application())
    }
}

