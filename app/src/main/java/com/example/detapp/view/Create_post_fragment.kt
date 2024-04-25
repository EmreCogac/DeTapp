package com.example.detapp.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.detapp.databinding.FragmentCreatePostFragmentBinding
import com.example.detapp.model.PostDataModel
import com.example.detapp.viewmodel.AuthViewModel
import com.example.detapp.viewmodel.PostViewModel
import com.google.firebase.auth.FirebaseUser
import java.time.LocalDate


class Create_post_fragment : Fragment() {

    private var _binding : FragmentCreatePostFragmentBinding? = null
    private val binding get() = _binding !!

    private var postViewModel: PostViewModel? = null
    private var authViewModel: AuthViewModel? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)).get(PostViewModel::class.java)
        authViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)).get(AuthViewModel::class.java)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePostFragmentBinding.inflate(inflater, container, false)


        binding.apply {
//            var kitapname = kitapadoEditText.text.toString()

            authViewModel?.userData?.observe(viewLifecycleOwner, Observer<FirebaseUser?> { firebaseUser ->
                firebaseUser.let {

                    kitapadoText.visibility = View.VISIBLE
                    kitapadoEditText.visibility = View.VISIBLE
                    postKitap.visibility = View.VISIBLE
                    hesap.visibility = View.GONE
//                    postdeneme.visibility = View.VISIBLE

                    val current = LocalDate.now().toString()
                    postKitap.setOnClickListener {

                        val kitapadi = kitapadoEditText.text.toString()
                        if(kitapadi.isNotEmpty()){
                            val model = PostDataModel(kitapadi,current, firebaseUser!!.email.toString())
                            postViewModel?.createPostFirestore(model)
                        }
                    }

                }
            })
//            postdeneme.setOnClickListener{
//                postViewModel?.firestore()
//            }

        }


        return binding.root
    }

}