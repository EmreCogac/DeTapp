package com.example.detapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.detapp.model.PostReadModel
import com.example.detapp.model.ProfileInfoDataModel
import com.example.detapp.repo.ProfileInfoRepo
import com.google.firebase.auth.FirebaseUser

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProfileInfoRepo
    private var userData: MutableLiveData<FirebaseUser?>
    private val loggedStatus: MutableLiveData<Boolean>
    private val _postReadModelList = MutableLiveData<List<PostReadModel>>()
    val postReadModelList: LiveData<List<PostReadModel>> = _postReadModelList

    init {
        repository = ProfileInfoRepo(application)
        userData = repository.firebaseUserMutableLiveData
        loggedStatus = repository.userLoggedMutableLiveData
        getProfilePostData()
    }

    fun deneme() : LiveData<ProfileInfoDataModel> {
        return repository.profileInfo()
    }
    fun deletePost(postid : String){
        repository.deletePost(postid)
    }
    private fun getProfilePostData() {
        repository.getProfilePostData{ postList ->
            _postReadModelList.value = postList
        }
    }
//    fun profileDeneme() : kotlinx.coroutines.flow.Flow<ProfileInfoDataModel> {
//
//        return repository.profileDeneme()
//
//    }



}