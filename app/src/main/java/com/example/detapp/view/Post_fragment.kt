package com.example.detapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayoutStates
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.detapp.R
import com.example.detapp.adapter.PostAdapter
import com.example.detapp.databinding.FragmentCreatePostFragmentBinding
import com.example.detapp.databinding.FragmentPostFragmentBinding
import com.example.detapp.model.PostDataModel
import com.example.detapp.model.PostReadModel
import com.example.detapp.viewmodel.AuthViewModel
import com.example.detapp.viewmodel.PostViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.firestore
import java.time.LocalDate


 class Post_fragment : Fragment() , PostAdapter.ItemClickListener {

    private var _binding : FragmentPostFragmentBinding? = null
    private val binding get() = _binding !!
    private lateinit var postViewModel: PostViewModel
    private var authViewModel: AuthViewModel? =null

     override fun onButtonClick(position: PostReadModel) {
         Toast.makeText(requireContext(), position.bookname, Toast.LENGTH_SHORT).show()
     }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)).get(PostViewModel::class.java)
        authViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(activity?.application!!)).get(AuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPostFragmentBinding.inflate(inflater, container, false)

            val recyclerView: RecyclerView = binding.recyclerPost
             recyclerView.layoutManager = LinearLayoutManager(requireContext())
            postViewModel.postReadModelList.observe(viewLifecycleOwner, Observer { postList ->

                recyclerView.adapter = PostAdapter(postList){
                   position -> onButtonClick(position)

                }
            })

        return binding.root
    }


}