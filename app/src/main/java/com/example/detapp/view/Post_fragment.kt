package com.example.detapp.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.detapp.R
import com.example.detapp.adapter.PostAdapter
import com.example.detapp.databinding.FragmentPostFragmentBinding
import com.example.detapp.model.PostReadModel
import com.example.detapp.viewmodel.AuthViewModel
import com.example.detapp.viewmodel.PostViewModel
import com.google.firebase.auth.FirebaseUser


class Post_fragment : Fragment() , PostAdapter.ItemClickListener {

    private var _binding : FragmentPostFragmentBinding? = null
    private val binding get() = _binding !!
    private lateinit var postViewModel: PostViewModel
    private var authViewModel: AuthViewModel? =null
    private lateinit var adapter: PostAdapter

     override fun onButtonClick(position: PostReadModel) {
//         Toast.makeText(requireContext(), position.bookname, Toast.LENGTH_SHORT).show()
         authViewModel?.userData?.observe(viewLifecycleOwner, Observer<FirebaseUser?> {firebaseuser->
             firebaseuser.let {
                 if (firebaseuser?.uid== null){
                     Toast.makeText(
                         requireContext(),
                         "Lütfen yapınız",
                         Toast.LENGTH_SHORT
                     ).show()
                     findNavController().navigate(R.id.action_post_fragment_to_user_fragment)
                 }
             }
         })

         val i = Intent(Intent.ACTION_SEND)
         i.setType("message/rfc822")
         i.putExtra(Intent.EXTRA_EMAIL, arrayOf(position.usermail))
         i.putExtra(Intent.EXTRA_SUBJECT, "Hey! sizinle değiş tokuş uygulaması ile kitap değişikliği yapmak istiyorum!")
         i.putExtra(Intent.EXTRA_TEXT, "")
         try {
             startActivity(Intent.createChooser(i, "Send mail..."))
         } catch (ex: ActivityNotFoundException) {
             Toast.makeText(
                 requireContext(),
                 "email uygulamasına sahip değilsin",
                 Toast.LENGTH_SHORT
             ).show()
         }
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
            val searchView : androidx.appcompat.widget.SearchView = binding.searchView

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            postViewModel.postReadModelList.observe(viewLifecycleOwner, Observer { postList ->

                adapter = PostAdapter(postList,postList) { position -> onButtonClick(position) }
                recyclerView.adapter = adapter

            })
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText.orEmpty())
                return true
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}